package org.antop.ktorm.model

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

open class Regions(alias: String?) : Table<Region>("HR_REGIONS", alias) {
    companion object : Regions(null)

    override fun aliased(alias: String) = Regions(alias)

    val id = int("REGION_ID").primaryKey().bindTo { it.id }
    val name = varchar("REGION_NAME").bindTo { it.name }
}

interface Region : Entity<Region> {
    companion object : Entity.Factory<Region>()

    var id: Int
    var name: String
}

val Database.regions get() = this.sequenceOf(Regions)
