package models.entity

/**
  * @author igorzg on 10.01.18.
  * @since 1.0
  */
case class Country(
                  country_id: Int,
                  iso: String,
                  name: String,
                  nicename: String,
                  iso3: String,
                  numcode: Short,
                  phonecode: Short
                  )
