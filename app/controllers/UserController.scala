package controllers

import java.util.UUID
import javax.inject.{Inject, Singleton}

import models.dao.UsersDao
import models.entity.User
import org.json4s.JsonAST.JString
import org.json4s.{DefaultFormats, FieldSerializer, Formats}
import org.json4s.native.Serialization.{write, writePretty}
import org.json4s.native.parseJsonOpt
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}


case class Credentials(username: String, password: String)

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


  def index() = Action.async { implicit request =>
    userDao.all().map {
      users => Ok(writePretty(users)(formats + ignoreFields))
    }
  }


  def authenticate() = Action.async { implicit request =>
    val data = parseJsonOpt(request.body.asText.get)
    if (data.isDefined) {
      val credentials: Option[Credentials] = data.get.extractOpt[Credentials]
      if (credentials.isDefined) {
        userDao.verifyUser(credentials.get.username, credentials.get.password).map {
          isLoggedIn: Boolean => {
            if (isLoggedIn) {
              Ok(
                write(
                  Map(
                    "message" -> "User successfully authenticated",
                    "token" -> UUID.randomUUID().toString
                  )
                )
              )
            } else Unauthorized

          }
        }
      } else Future.successful(
        BadRequest(
          write(
            "message" -> JString("Invalid parameters {username, password}")
          )
        )
      )
    } else
      Future.successful(
        BadRequest(
          write(
            "message" -> JString("Authenticate parameters has to be provided {username, password}")
          )
        )
      )
  }

}