package org.antop.ktorm.repository

import org.antop.ktorm.common.KtormRepository
import org.antop.ktorm.model.Region
import org.antop.ktorm.model.regions
import org.ktorm.database.Database
import org.ktorm.dsl.asc
import org.ktorm.dsl.eq
import org.ktorm.dsl.inList
import org.ktorm.entity.add
import org.ktorm.entity.count
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.forEach
import org.ktorm.entity.sortedBy
import org.ktorm.entity.toMutableList
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

@Repository
@Transactional(readOnly = true)
class RegionRepository(
    private val database: Database,
) : KtormRepository<Region, Int> {
    @Transactional
    override fun <S : Region?> save(entity: S & Any): S & Any {
        database.regions.add(entity)
        return entity
    }

    @Transactional
    override fun <S : Region> saveAll(entities: Iterable<S>): List<S> {
        entities.forEach { save(it) }
        return entities.toList()
    }

    override fun findById(id: Int): Optional<Region> {
        val entity = database.regions.find { it.id eq id }
        return Optional.ofNullable(entity)
    }

    override fun findAll(): List<Region> = database.regions.sortedBy { it.id.asc() }.toMutableList()

    override fun findAllById(ids: Iterable<Int>): List<Region> =
        database.regions
            .filter {
                it.id inList ids.toList()
            }.toMutableList()

    override fun count(): Long = database.regions.count().toLong()

    @Transactional
    override fun deleteById(id: Int) {
        findByIdOrNull(id)?.delete()
    }

    @Transactional
    override fun delete(entity: Region) {
        deleteById(entity.id)
    }

    @Transactional
    override fun deleteAll() {
        database.regions.forEach { delete(it) }
    }
}
