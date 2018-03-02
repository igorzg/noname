package models.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import macros.models.Entity

/**
  * @author igorzg on 10.01.18.
  * @since 1.0
  */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
case class Country(
    var country_id: Option[Int],
    iso: String,
    name: String,
    var iso3: Option[String],
    var num_code: Option[Short],
    phone_code: Short
)
