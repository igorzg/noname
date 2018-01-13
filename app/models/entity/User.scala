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
                 password: String,
                 salt: String,
                 birth: Date,
                 gender: String,
                 country_id: Int
               )
