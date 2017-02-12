package example

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route


object HttpInterface {
  def routes: Route =
    path("hello") {
      complete("openshift")
    }
}
