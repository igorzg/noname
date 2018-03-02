package models.dao

import javax.inject.Inject

import models.entity.Country
import models.Tables
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author igorzg on 10.01.18.
  * @since 1.0
  */
class CountriesDao @Inject()(
    protected val dbConfigProvider: DatabaseConfigProvider)(
    implicit ec: ExecutionContext)
    extends Tables
    with HasDatabaseConfig[JdbcProfile] {

  override protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import profile.api._

  lazy val query = TableQuery[CountriesTable]

  def all(): Future[Seq[Country]] = db.run(query.result).map(_.toList)

}
