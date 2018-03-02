package models

import java.sql.Timestamp
import java.util.Date

import models.entity._
import play.api.db.slick.HasDatabaseConfig
import slick.jdbc.JdbcProfile

/**
  * @author igorzg on 02.03.18.
  * @since 1.0
  */
trait Tables {

  self: HasDatabaseConfig[JdbcProfile] =>

  import profile.api._

  /**
    * COUNTRIES
    *
    * @param tag
    */
  class CountriesTable(tag: Tag) extends Table[Country](tag, "Countries") {

    def country_id = column[Option[Int]]("country_id", O.PrimaryKey, O.AutoInc)

    def iso = column[String]("iso")

    def name = column[String]("name")

    def iso3 = column[String]("iso3")

    def num_code = column[Short]("num_code")

    def phone_code = column[Short]("phone_code")

    def * =
      (
        country_id,
        iso,
        name,
        iso3,
        num_code,
        phone_code
      ) <> (Country.tableApply, Country.tableUnapply)
  }

  /**
    * PERMISIONS
    *
    * @param tag
    */
  class PermissionsTable(tag: Tag)
    extends Table[Permission](tag, "Permissions") {

    def perm_id = column[Option[Int]]("perm_id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def action = column[String]("action")

    def * =
      (perm_id, name, action) <> (Permission.tableApply, Permission.tableUnapply)
  }

  /**
    * Permission role
    *
    * @param tag
    */
  class RolePermissionTable(tag: Tag)
    extends Table[RolePermission](tag, "Role_Permission") {
    def roleId = column[Int]("role_id")

    def permId = column[Int]("perm_id")

    def * = (roleId, permId) <> (RolePermission.tupled, RolePermission.unapply)

    def perm = foreignKey("Role_Permission_perm_id", permId, TableQuery[PermissionsTable])(_.perm_id.get)

    def role = foreignKey("Role_Permission_role_id", roleId, TableQuery[RolesTable])(_.role_id.get)
  }

  /**
    * Roles table
    *
    * @param tag
    */
  class RolesTable(tag: Tag) extends Table[Role](tag, "Roles") {

    def role_id = column[Option[Int]]("role_id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (role_id, name) <> (Role.tableApply, Role.tableUnapply)
  }

  /**
    * User Role table
    *
    * @param tag
    */
  class UserRoleTable(tag: Tag) extends Table[UserRole](tag, "User_Role") {
    def userId = column[Int]("user_id")

    def roleId = column[Int]("role_id")

    def * = (userId, roleId) <> (UserRole.tupled, UserRole.unapply)

    def user = foreignKey("User_Role_user_id", userId, TableQuery[UsersTable])(_.user_id.get)

    def role = foreignKey("User_Role_role_id", roleId, TableQuery[RolesTable])(_.role_id.get)
  }

  /**
    * Users table definitions
    *
    * @return
    */
  implicit def rdsTimestampToDate = MappedColumnType.base[Date, Timestamp](
    dt => Timestamp.from(dt.toInstant),
    ts => Date.from(ts.toInstant)
  )

  class UsersTable(tag: Tag) extends Table[User](tag, "Users") {

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

    def * =
      (
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
      ) <> (User.tableApply, User.tableUnapply)

    def country =
      foreignKey("Users_country_id", country_id, TableQuery[CountriesTable])(
        _.country_id.get)
  }

}
