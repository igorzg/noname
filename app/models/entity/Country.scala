package models.entity

/**
  * @author igorzg on 10.01.18.
  * @since 1.0
  */
case class Country(
                    country_id: Option[Int],
                    iso: String,
                    name: String,
                    iso3: String,
                    num_code: Short,
                    phone_code: Short
                  )
