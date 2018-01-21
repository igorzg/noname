package models.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import models.macros.Entity

/**
  * @author igorzg on 10.01.18.
  * @since 1.0
  */
// @Entity
@JsonIgnoreProperties(ignoreUnknown = true)
case class Country(
                    country_id: Option[Int],
                    iso: String,
                    name: String,
                    iso3: String,
                    num_code: Short,
                    phone_code: Short
                  )
