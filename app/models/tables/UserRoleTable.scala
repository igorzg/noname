package models.tables

import models.entity.UserRole
import play.api.db.slick.HasDatabaseConfig
import slick.jdbc.JdbcProfile

/**
  * @author igorzg on 03.02.18.
  * @since 1.0
  */
trait UserRoleTable {
  self: HasDatabaseConfig[JdbcProfile] =>

  import profile.api._

  class UserRoleTable(tag: Tag) extends Table[UserRole](tag, "User_Role") {
    def userId = column[Int]("user_id")

    def roleId = column[Int]("role_id")

    def * = (userId, roleId) <> (UserRole.tupled, UserRole.unapply)
  }
}
