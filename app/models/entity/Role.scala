package models.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
  * @author igorzg on 14.01.18.
  * @since 1.0
  */
@JsonIgnoreProperties(ignoreUnknown = true)
case class Role(role_id: Option[Int], name: String)
