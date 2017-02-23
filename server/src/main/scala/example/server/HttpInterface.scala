package example.server

import akka.event.Logging
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.typesafe.scalalogging.LazyLogging


object HttpInterface extends LazyLogging {
  def routes(msg: String): Route =
    logRequestResult("server-request", Logging.InfoLevel) {
      get {
        pathPrefix("hello") {
          complete(msg)
        }
      }
    }
}
