package controllers

import javax.inject.{Inject, Singleton}

import models.dao.PermissionsDao
import org.json4s.jackson.Serialization.writePretty
import org.json4s.{DefaultFormats, Formats}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  *
  * @author igorzg on 04.01.18.
  * @since 1.0
  */
@Singleton
class PermissionsController @Inject()(cc: ControllerComponents, permissionsDao: PermissionsDao)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  implicit val usersFormats: Formats = DefaultFormats

  def index() = Action.async { implicit request =>
    permissionsDao.all().map {
      permissions => Ok(writePretty(permissions))
    }
  }

}