package models.tables

import models.entity.{Permission, Role}
import play.api.db.slick.HasDatabaseConfig
import slick.jdbc.JdbcProfile

/**
  * @author igorzg on 14.01.18.
  * @since 1.0
  */
trait PermissionsTable {
  self: HasDatabaseConfig[JdbcProfile] =>

  import profile.api._

  class PermissionsTable(tag: Tag) extends Table[Permission](tag, "Permissions") {

    def perm_id = column[Option[Int]]("perm_id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def action = column[String]("action")

    def * = (perm_id, name, action) <> ((Permission.apply _).tupled, Permission.unapply)
  }

}
