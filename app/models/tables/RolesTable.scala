package models.tables

import models.entity.{Role, User}
import play.api.db.slick.HasDatabaseConfig
import slick.jdbc.JdbcProfile

/**
  * @author igorzg on 14.01.18.
  * @since 1.0
  */
trait RolesTable {
  self: HasDatabaseConfig[JdbcProfile] =>

  import profile.api._

  class RolesTable(tag: Tag) extends Table[Role](tag, "Roles") {

    def role_id = column[Option[Int]]("role_id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def * = (role_id, name) <> (Role.tableApply, Role.tableUnapply)
  }

}
