package controllers

import java.util.UUID
import javax.inject.{Inject, Singleton}

import com.fasterxml.jackson.core.`type`.TypeReference
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}
import models.Credentials
import models.dao.UsersDao
import models.entity.User
import org.json4s.{DefaultFormats, FieldSerializer, Formats}
import helpers.JsonHelper._

import scala.concurrent.{ExecutionContext, Future}

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  *
  * @author igorzg on 04.01.18.
  * @since 1.0
  */
@Singleton
class UserController @Inject()(cc: ControllerComponents, userDao: UsersDao)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  private val ignoreFields = FieldSerializer[User](
    {
      case ("salt", _) => None
      case ("password", _) => None
    }
  )

  private implicit val userType: TypeReference[User] = new TypeReference[User]() {}

  private implicit val credentialsType: TypeReference[Credentials] = new TypeReference[Credentials]() {}

  private implicit val formats: Formats = DefaultFormats

  def index(): Action[AnyContent] = Action.async { implicit request =>
    userDao.all().map { users =>
      Ok(write(users)(formats + ignoreFields))
    }
  }

  def get(user_id: Int): Action[AnyContent] = Action.async { implicit request =>
    userDao.findById(user_id).map { user =>
      val value = mapper.writeValueAsString(user.get)
      Ok(value)
    }
  }

  def update(): Action[AnyContent] = Action.async { implicit request =>
    val user = parseOpt[User](request.body.asText.get)
    if (user.isDefined) {
      if (user.isDefined) {
        if (user.get.user_id.isDefined) {
          userDao.updateById(user.get).map { result =>
            Ok(result.toString)
          }
        } else {
          Future.successful(
            BadRequest(
              write(
                Map(
                  "message" -> "Missing user_id"
                )
              )))
        }
      } else {
        Future.successful(
          BadRequest(
            write(
              Map(
                "message" -> "Cannot cast json to User"
              )
            )))
      }
    } else {
      Future.successful(
        BadRequest(
          write(
            Map(
              "message" -> "Cannot parse json"
            )
          )))
    }
  }

  def authenticate(): Action[AnyContent] = Action.async { implicit request =>
    val credentials = parseOpt[Credentials](request.body.asText.get)
    if (credentials.isDefined) {
      userDao
        .verifyUser(credentials.get.username, credentials.get.password)
        .map { isLoggedIn: Boolean => {
          if (isLoggedIn) {
            Ok(
              write(
                Map(
                  "message" -> "User successfully authenticated",
                  "token" -> UUID
                    .randomUUID()
                    .toString
                    .concat("-" + System.currentTimeMillis().toString)
                )
              ))
          } else Unauthorized
        }
        }
    } else {
      Future.successful(
        BadRequest(
          write(
            Map(
              "message" -> "Authenticate parameters has to be provided {username, password}"
            )
          )))
    }
  }

}
