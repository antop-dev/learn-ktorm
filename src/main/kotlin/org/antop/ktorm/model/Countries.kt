package org.antop.ktorm.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

open class Countries(alias: String?) : Table<Country>("HR_COUNTRIES", alias) {
    companion object : Countries(null)

    override fun aliased(alias: String) = Countries(alias)

    val id = varchar("COUNTRY_ID").primaryKey().bindTo { it.id }
    val name = varchar("COUNTRY_NAME").bindTo { it.name }
    private val regionId = int("REGION_ID").references(Regions) { it.region }

    val region get() = regionId.referenceTable as Regions
}

interface Country : Entity<Country> {
    companion object : Entity.Factory<Country>()

    var id: String
    var name: String
    var region: Region
}

val Database.countries get() = this.sequenceOf(Countries)
