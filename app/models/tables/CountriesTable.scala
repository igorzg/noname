package models.tables

import models.entity.Country
import play.api.db.slick.HasDatabaseConfig
import slick.jdbc.JdbcProfile

/**
  * @author igorzg on 10.01.18.
  * @since 1.0
  */
trait CountriesTable {

  self: HasDatabaseConfig[JdbcProfile] =>

  import profile.api._

  class CountriesTable(tag: Tag) extends Table[Country](tag, "Countries") {

    def country_id = column[Option[Int]]("country_id", O.PrimaryKey, O.AutoInc)

    def iso = column[String]("iso")

    def name = column[String]("name")

    def iso3 = column[String]("iso3")

    def num_code = column[Short]("num_code")

    def phone_code = column[Short]("phone_code")

    def * = (
      country_id,
      iso,
      name,
      iso3,
      num_code,
      phone_code
      ) <> ((Country.apply _).tupled, Country.unapply _)
  }

}
