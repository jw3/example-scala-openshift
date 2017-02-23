package example.client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import com.typesafe.scalalogging.LazyLogging
import example.client.Util._
import example.common._

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}


object Boot extends App with LazyLogging {
  implicit val system = ActorSystem("example-s2i-client")
  implicit val mat = ActorMaterializer()
  import system.dispatcher

  val conf = system.settings.config.getConfig("server")
  val host = conf.getString("host")
  val port = conf.getInt("port")
  val path = conf.getString("path")
  implicit val conn = Http().outgoingConnection(host, port)

  system.scheduler.schedule(0 seconds, 5 seconds) {
    ping(path).foreach(v ⇒ logger.info(v))
  }
}

object Util {
  def ping(path: String)(implicit system: ActorSystem, mat: ActorMaterializer, ec: ExecutionContext, conn: Connection): Future[String] = {
    get(path).via(conn)
    .map(
      _.entity.dataBytes.map(_.utf8String).runWith(Sink.head)
    )
    .runWith(Sink.head)
    .flatMap(_.map(v ⇒ v))
  }
}
