package models.entity

import java.util.Date

import com.fasterxml.jackson.annotation.{JsonIgnore, JsonIgnoreProperties}
import macros.models.Entity
import macros.models.annotations.IgnoreOnSlackQuery

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
    @IgnoreOnSlackQuery var country: Option[Country] = None
)


//
//object User {
//  def apply(
//             arg: (
//               Option[Int],
//                 String,
//                 String,
//                 String,
//                 String,
//                 String,
//                 String,
//                 Date,
//                 String,
//                 Int
//               )
//           ): User = {
//    User(
//      arg._1,
//      arg._2,
//      arg._3,
//      arg._4,
//      arg._5,
//      arg._6,
//      arg._7,
//      arg._8,
//      arg._9,
//      arg._10
//    )
//  }
//  def unapply(user: User): Option[
//    scala.Tuple10[Option[Int],
//                  String,
//                  String,
//                  String,
//                  String,
//                  String,
//                  String,
//                  Date,
//                  String,
//                  Int]] =
//    Some(
//      scala.Tuple10(user.user_id,
//                    user.first_name,
//                    user.last_name,
//                    user.username,
//                    user.email,
//                    user.password,
//                    user.salt,
//                    user.birth,
//                    user.gender,
//                    user.country_id))
//}
