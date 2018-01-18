package controllers

import javax.inject.{Inject, Singleton}

import models.ServiceStatus
import org.json4s.{Formats, NoTypeHints}
import play.api.Configuration
import play.api.mvc.{AbstractController, ControllerComponents}
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.write

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  *
  * @author igorzg on 04.01.18.
  * @since 1.0
  */
@Singleton
class StatusController @Inject()(cc: ControllerComponents, configuration: Configuration) extends AbstractController(cc) {

  implicit val statusFormats: Formats = Serialization.formats(NoTypeHints)

  def index() = Action {
    Ok(
      write(
        ServiceStatus(
          configuration.underlying.getString("service"),
          configuration.underlying.getString("environment"),
          configuration.underlying.getString("version"),
          "Service is up an running"
        )
      )
    )
  }

}