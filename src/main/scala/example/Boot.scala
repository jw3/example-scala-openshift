package example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging

import scala.util.{Failure, Success}


object Boot extends App with LazyLogging {
  implicit val system = ActorSystem("examples2i")
  implicit val mat = ActorMaterializer()

  import system.dispatcher

  val conf = system.settings.config.getConfig("http")
  val host = conf.getString("host")
  val port = conf.getInt("port")

  Http().bindAndHandle(HttpInterface.routes, host, port).onComplete {
    case Success(_) => logger.info(s"started http at $host:$port")
    case Failure(e) => logger.error(s"failed to start http at $host:$port", e)
  }
}
