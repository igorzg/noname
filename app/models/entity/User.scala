package models.entity

import java.util.Date
import macros.models.annotations.Transient
import macros.models.Entity
import com.fasterxml.jackson.annotation.{JsonIgnore, JsonIgnoreProperties}

/**
  * @author igorzg on 10.01.18.
  * @since 1.0
  */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
case class User(
                 var user_id: Option[Int],
                 first_name: String,
                 last_name: String,
                 username: String,
                 email: String,
                 @JsonIgnore password: String,
                 @JsonIgnore salt: String,
                 birth: Date,
                 gender: String,
                 @JsonIgnore country_id: Int,
                 @Transient var country: Option[Country] = None
               ) {
  def merge(user: User): User = {
    User(
      user_id,
      user.first_name,
      user.last_name,
      user.username,
      user.email,
      password,
      salt,
      user.birth,
      user.gender,
      user.country_id
    )
  }

}

/**
  * User methods
  */
object User {

  def unapply(arg: User): Option[(
    Option[Int],
      String,
      String,
      String,
      String,
      String,
      String,
      Date,
      String,
      Int
    )] = {
    Some((arg.user_id,
      arg.first_name,
      arg.last_name,
      arg.username,
      arg.email,
      arg.password,
      arg.salt,
      arg.birth,
      arg.gender,
      arg.country_id))
  }

  def apply(
             arg: (
               Option[Int],
                 String,
                 String,
                 String,
                 String,
                 String,
                 String,
                 Date,
                 String,
                 Int
               )
           ): User = {
    User(
      arg._1,
      arg._2,
      arg._3,
      arg._4,
      arg._5,
      arg._6,
      arg._7,
      arg._8,
      arg._9,
      arg._10
    )
  }

}
