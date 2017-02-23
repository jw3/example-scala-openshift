package example.common

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}


case class Load(pct: Double, time: Int)
object Load extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val loadFormat: RootJsonFormat[Load] = jsonFormat2(Load.apply)
}
