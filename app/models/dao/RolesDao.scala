package models.dao

import javax.inject.Inject

import models.entity.Role
import models.tables.RolesTable
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author igorzg on 14.01.18.
  * @since 1.0
  */
class RolesDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends RolesTable with HasDatabaseConfig[JdbcProfile]  {

  override protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import profile.api._

  private lazy val query = TableQuery[RolesTable]

  def all(): Future[Seq[Role]] = db.run(query.result).map(_.toList)

}
