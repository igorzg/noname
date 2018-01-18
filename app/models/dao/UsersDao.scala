package models.dao

import javax.inject.Inject

import models.entity.User
import models.tables.UsersTable
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.jdbc.JdbcProfile
import com.github.t3hnar.bcrypt._
import play.Logger

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author igorzg on 10.01.18.
  * @since 1.0
  */

class UsersDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends UsersTable with HasDatabaseConfig[JdbcProfile] {

  override protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import profile.api._

  private lazy val query = TableQuery[UsersTable]

  def all(): Future[Seq[User]] = db.run(query.result).map(_.toList)

  def findByUsername(username: String): Future[Option[User]] = {
    db.run(query.filter(user => user.email === username || user.username === username).result.headOption)
  }

  def findById(id: Int): Future[Option[User]] = {
    db.run(query.filter(_.user_id === id).result.headOption)
  }

  def updateById(user: User): Future[Int] = {
    db.run(
      query.filter(_.user_id === user.user_id)
        .map(u => (
          u.first_name,
          u.last_name,
          u.username,
          u.email,
          u.birth,
          u.gender,
          u.country_id
        ))
        .update(
          user.first_name,
          user.last_name,
          user.username,
          user.email,
          user.birth,
          user.gender,
          user.country_id
        )
    )
  }

  def verifyUser(username: String, password: String): Future[Boolean] = {
    findByUsername(username)
      .map {
        case Some(user: User) => {
          val salt = generateSalt
          Logger.debug("password {} salt {} ", password.bcrypt(salt), salt)

          try {
            Logger.debug("IsBecrypt {} salt {} info {}", password, user.salt, password.isBcrypted(user.password).toString)
            password.isBcrypted(user.password)
          } catch {
            case e: Exception =>
              Logger.error("Invalid salt", e)
              false
          }
        }
        case None => false
      }
  }

}
