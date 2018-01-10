package models.tables

import java.util.Date

import models.entity.User
import play.api.db.slick.HasDatabaseConfig
import slick.jdbc.JdbcProfile

/**
  * @author igorzg on 10.01.18.
  * @since 1.0
  */


trait UserTable {
  self: HasDatabaseConfig[JdbcProfile] =>

  import profile.api._

  class UserTable(tag: Tag) extends Table[User](tag, "User") {

    def user_id = column[Option[Int]]("user_id", O.PrimaryKey, O.AutoInc)

    def first_name = column[String]("first_name")

    def last_name = column[String]("last_name")

    def username = column[String]("username")

    def email = column[String]("email")

    def password = column[String]("password")

    def salt = column[String]("salt")

    def birth = column[Date]("birth")

    def gender = column[String]("gender")

    def country_id = column[Int]("country_id")

    def * = (
      user_id,
      first_name,
      last_name,
      username,
      email,
      password,
      salt,
      birth,
      gender,
      country_id
    ) <> ((User.apply _).tupled, User.unapply _)
  }

}
