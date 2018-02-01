package models.entity

import java.util.Date

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonIgnoreProperties}
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import helpers.deserializer.OptionIntDeserializer
import macros.models.Entity
import macros.models.annotations.IgnoreOnSlickTableMapping

/**
  * @author igorzg on 10.01.18.
  * @since 1.0
  */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
case class User(
    @JsonDeserialize(using = classOf[OptionIntDeserializer]) var user_id: Option[Int],
    first_name: String,
    last_name: String,
    username: String,
    email: String,
    @JsonIgnore password: String,
    @JsonIgnore salt: String,
    birth: Date,
    gender: String,
    @JsonIgnore country_id: Int,
    @IgnoreOnSlickTableMapping var country: Option[Country] = None
)
