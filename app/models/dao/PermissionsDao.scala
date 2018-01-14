package models.dao

import javax.inject.Inject

import models.entity.Permission
import models.tables.PermissionsTable
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author igorzg on 14.01.18.
  * @since 1.0
  */
class PermissionsDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends PermissionsTable with HasDatabaseConfig[JdbcProfile] {

  override protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import profile.api._

  private lazy val query = TableQuery[PermissionsTable]

  def all(): Future[Seq[Permission]] = db.run(query.result).map(_.toList)

}
