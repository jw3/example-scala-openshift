package example.server

import akka.event.Logging
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.typesafe.scalalogging.LazyLogging
import example.common.Load
import example.server.FakeLoad._


object HttpInterface extends LazyLogging {
  def routes(msg: String): Route =
    logRequestResult("server-request", Logging.InfoLevel) {
      get {
        path("hello") {
          complete(msg)
        }
      } ~
        post {
          path("load") {
            entity(as[Load]) { e ⇒
              generate(e)
              complete("OK")
            }
          }
        }
    }
}
