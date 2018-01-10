package controllers

import javax.inject.{Inject, Singleton}

import models.ServiceStatus
import play.api.Configuration
import play.api.mvc.{AbstractController, ControllerComponents}

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  *
  * @author igorzg on 04.01.18.
  * @since 1.0
  */
@Singleton
class StatusController @Inject()(cc: ControllerComponents, configuration: Configuration) extends AbstractController(cc) {


  def index() = Action { implicit request =>
    Ok(
      ServiceStatus(
        configuration.underlying.getString("service"),
        configuration.underlying.getString("environment"),
        configuration.underlying.getString("version"),
        "Service is up an running"
      )
    )
  }

}