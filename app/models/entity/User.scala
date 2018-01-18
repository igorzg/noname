package models.entity

import java.util.Date

/**
  * @author igorzg on 10.01.18.
  * @since 1.0
  */
case class User(
                 var user_id: Option[Int],
                 first_name: String,
                 last_name: String,
                 username: String,
                 email: String,
                 password: String = "",
                 salt: String = "",
                 birth: Date,
                 gender: String,
                 country_id: Int
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
