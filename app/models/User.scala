package models

import javax.inject.Inject

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import play.api.libs.json.Json
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author igorzg on 04.01.18.
  * @since 1.0
  */
case class User(var user_id: Option[Int], name: String)

object User {
  implicit val userFormat = Json.format[User]
}

trait UserTable {
  self: HasDatabaseConfig[JdbcProfile] =>

  import profile.api._

  class UserTable(tag: Tag) extends Table[User](tag, "User") {

    def user_id = column[Option[Int]]("user_id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (user_id, name) <> ((User.apply _).tupled, User.unapply _)
  }

}

class UserDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends UserTable with HasDatabaseConfig[JdbcProfile] {

  override protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import profile.api._

  private lazy val users = TableQuery[UserTable]

  def all(): Future[Seq[User]] = db.run(users.result).map(_.toList)


}
