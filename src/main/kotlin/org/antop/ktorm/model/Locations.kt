package org.antop.ktorm.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

open class Locations(alias: String?) : Table<Location>("HR_LOCATIONS", alias) {
    companion object : Locations(null)

    override fun aliased(alias: String) = Locations(alias)

    val id = int("LOCATION_ID").primaryKey().bindTo { it.id }
    val streetAddress = varchar("STREET_ADDRESS").bindTo { it.streetAddress }
    val postalCode = varchar("POSTAL_CODE").bindTo { it.postalCode }
    val city = varchar("CITY").bindTo { it.city }
    val stateProvince = varchar("STATE_PROVINCE").bindTo { it.stateProvince }
    private val countryId = varchar("COUNTRY_ID").references(Countries, selector = { it.country })

    val country get() = countryId.referenceTable as Countries
}

interface Location : Entity<Location> {
    companion object : Entity.Factory<Location>()

    var id: Int
    var streetAddress: String
    var postalCode: String
    var city: String
    var stateProvince: String?
    var country: Country
}

val Database.locations get() = this.sequenceOf(Locations)
