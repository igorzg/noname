package models.dao

import javax.inject.Inject

import models.entity.User
import models.tables.UsersTable
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author igorzg on 10.01.18.
  * @since 1.0
  */

class UsersDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends UsersTable with HasDatabaseConfig[JdbcProfile] {

  override protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import profile.api._

  private lazy val users = TableQuery[UsersTable]

  def all(): Future[Seq[User]] = db.run(users.result).map(_.toList)

}
