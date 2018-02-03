package models.tables

import models.entity.RolePermission
import play.api.db.slick.HasDatabaseConfig
import slick.jdbc.JdbcProfile

/**
  * @author igorzg on 03.02.18.
  * @since 1.0
  */
trait RolePermissionTable {

  self: HasDatabaseConfig[JdbcProfile] =>

  import profile.api._

  class RolePermissionTable(tag: Tag) extends Table[RolePermission](tag, "Role_Permission") {
    def roleId = column[Int]("role_id")

    def permId = column[Int]("perm_id")

    def * = (roleId, permId) <> (RolePermission.tupled, RolePermission.unapply)
  }

}
