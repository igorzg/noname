package controllers

import javax.inject.{Inject, Singleton}

import models.dao.UsersDao
import models.entity.User
import org.json4s.{DefaultFormats, FieldSerializer, Formats}
import org.json4s.native.Serialization.{write, writePretty}
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
class UserController @Inject()(cc: ControllerComponents, userDao: UsersDao)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  private val ignoreFields = FieldSerializer[User](
    {
      case ("salt", _) => None
      case ("password", _) => None
    }
  )

  implicit val usersFormats: Formats = DefaultFormats + ignoreFields

  def index() = Action.async { implicit request =>
    userDao.all().map {
      users => Ok(writePretty(users))
    }
  }

}