package example

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse}
import akka.stream.scaladsl.{Flow, Source}

import scala.concurrent.Future


package object common {
  type Connection = Flow[HttpRequest, HttpResponse, _]
  type RequestBuilder = HttpRequest => Future[HttpRequest]

  val apiPrefix: String = ""

  def uripath(suffix: String) = s"/$apiPrefix$suffix"

  /**
   * Create a GET HttpRequest as a Source
   */
  def get(path: String)(implicit system: ActorSystem): Source[HttpRequest, NotUsed] = {
    request(path)(r => Future.successful(r.withMethod(HttpMethods.GET)))
  }


  /**
   * Create a POST HttpRequest as a Source
   */
  def post(path: String)(implicit system: ActorSystem): Source[HttpRequest, NotUsed] = {
    request(path)(r => Future.successful(r.withMethod(HttpMethods.POST)))
  }


  /**
   * Create a HttpRequest as a Source with access to modify its construction
   */
  def request(path: String)(builder: RequestBuilder)(implicit system: ActorSystem): Source[HttpRequest, NotUsed] = {
    Source.fromFuture(builder(HttpRequest(uri = uripath(path))))
  }
}
