package models

import play.api.libs.json.Json

/**
  * @author igorzg on 10.01.18.
  * @since 1.0
  */
case class ServiceStatus(service: String, environment: String, version: String, message: String)

object ServiceStatus {
  implicit val jsonFormat = Json.format[ServiceStatus]
}