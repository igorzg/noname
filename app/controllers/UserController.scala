package controllers

import java.util.UUID
import javax.inject.{Inject, Singleton}

import models.Credentials
import models.dao.UsersDao
import models.entity.User
import org.json4s.{DefaultFormats, FieldSerializer, Formats}
import org.json4s.native.Serialization.{write, writePretty}
import org.json4s.native.JsonMethods._
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

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

  implicit val formats: Formats = DefaultFormats

  def index(): Action[AnyContent] = Action.async { implicit request =>
    userDao.all().map {
      users => Ok(write(users)(formats + ignoreFields))
    }
  }

  def update(): Action[AnyContent] = Action.async { implicit request =>
    val data = parseOpt(request.body.asText.get)
    if (data.isDefined) {
      val user: Option[User] = data.get.extractOpt[User]
      if (user.isDefined) {
        if (user.get.user_id.isDefined) {
          userDao.updateById(user.get).map {
            result => Ok(result.toString)
          }
        } else {
          Future.successful(BadRequest(write(
            Map(
              "message" -> "Missing user_id"
            )
          )))
        }
      } else {
        Future.successful(BadRequest(write(
          Map(
            "message" -> "Cannot cast json to User"
          )
        )))
      }
    } else {
      Future.successful(BadRequest(write(
        Map(
          "message" -> "Cannot parse json"
        )
      )))
    }
  }

  def authenticate(): Action[AnyContent] = Action.async { implicit request =>
    val data = parseOpt(request.body.asText.get)
    if (data.isDefined) {
      val credentials: Option[Credentials] = data.get.extractOpt[Credentials]
      if (credentials.isDefined) {
        userDao.verifyUser(credentials.get.username, credentials.get.password).map {
          isLoggedIn: Boolean => {
            if (isLoggedIn) {
              Ok(write(
                Map(
                  "message" -> "User successfully authenticated",
                  "token" -> UUID.randomUUID().toString.concat("-" + System.currentTimeMillis().toString)
                )
              ))
            } else Unauthorized
          }
        }
      } else {
        Future.successful(BadRequest(write(
          Map(
            "message" -> "Invalid parameters {username, password}"
          )
        )))
      }
    } else {
      Future.successful(BadRequest(write(
        Map(
          "message" -> "Authenticate parameters has to be provided {username, password}"
        )
      )))
    }
  }

}