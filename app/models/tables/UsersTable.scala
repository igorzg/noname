package models.tables

import models.entity.User
import play.api.db.slick.HasDatabaseConfig
import slick.jdbc.JdbcProfile

/**
  * @author igorzg on 10.01.18.
  * @since 1.0
  */


trait UsersTable {
  self: HasDatabaseConfig[JdbcProfile] =>

  import profile.api._

  class UsersTable(tag: Tag) extends Table[User](tag, "Users") {

    def user_id = column[Option[Int]]("user_id", O.PrimaryKey, O.AutoInc)

    def first_name = column[String]("first_name")

    def last_name = column[String]("last_name")

    def username = column[String]("username")

    def email = column[String]("email")

    def password = column[String]("password")

    def salt = column[String]("salt")

    def birth = column[String]("birth")

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
