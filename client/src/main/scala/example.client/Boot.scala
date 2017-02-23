package example.client

import java.time.Duration

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.MessageEntity
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import com.typesafe.scalalogging.LazyLogging
import example.client.Util._
import example.common._

import scala.concurrent.duration.DurationLong
import scala.concurrent.{ExecutionContext, Future}


object Boot extends App with LazyLogging {
  implicit val system = ActorSystem("example-s2i-client")
  implicit val mat = ActorMaterializer()
  import system.dispatcher

  val conf = system.settings.config.getConfig("server")
  val host = conf.getString("host")
  val port = conf.getInt("port")
  val pct = conf.getDouble("load.pct")
  val time = conf.getDuration("load.time")
  val helloInterval = conf.getDuration("ping.hello").getSeconds
  val loadInterval = conf.getDuration("ping.load").getSeconds
  val loadEnabled = conf.getBoolean("load.enabled")
  implicit val conn = Http().outgoingConnection(host, port)

  system.scheduler.schedule(0 seconds, helloInterval seconds) {
    ping.foreach(v ⇒ logger.info(v))
  }

  if (loadEnabled) {
    system.scheduler.schedule(0 seconds, loadInterval seconds) {
      logger.info(s"generating $pct% load for $time")
      load(pct, time)
    }
  }
  else logger.info("load generation is disabled")
}

object Util {
  def ping(implicit system: ActorSystem, mat: ActorMaterializer, ec: ExecutionContext, conn: Connection): Future[String] = {
    get("hello").via(conn)
    .map(
      _.entity.dataBytes.map(_.utf8String).runWith(Sink.head)
    )
    .runWith(Sink.head)
    .flatMap(_.map(v ⇒ v))
  }

  def load(pct: Double, duration: Duration)(implicit system: ActorSystem, mat: ActorMaterializer, ec: ExecutionContext, conn: Connection): Future[Done] = {
    post("load").mapAsync(1)(r ⇒ Marshal(Load(pct, duration.getSeconds.toInt)).to[MessageEntity].map(r.withEntity))
    .via(conn)
    .runWith(Sink.ignore)
  }
}
