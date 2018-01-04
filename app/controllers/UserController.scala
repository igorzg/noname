package controllers

import javax.inject.{Inject, Singleton}

import models.UserDao
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

/**
  * @author igorzg on 04.01.18.
  * @since 1.0
  */
@Singleton
class UserController @Inject()(cc: ControllerComponents, userDao: UserDao)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index() = Action.async { implicit request =>
    userDao.all().map {
      users => Ok(Json.toJson(users))
    }
  }

}