package models.dao

import javax.inject.Inject

import models.entity.Country
import models.tables.CountriesTable
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author igorzg on 10.01.18.
  * @since 1.0
  */
class CountriesDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends CountriesTable with HasDatabaseConfig[JdbcProfile]  {

  override protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import profile.api._

  private lazy val countries = TableQuery[CountriesTable]

  def all(): Future[Seq[Country]] = db.run(countries.result).map(_.toList)

}
