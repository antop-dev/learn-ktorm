package org.antop.ktorm.service

import org.antop.ktorm.dto.RegionDto
import org.antop.ktorm.model.Region
import org.antop.ktorm.model.regions
import org.antop.ktorm.repository.RegionRepository
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RegionService(
    private val repository: RegionRepository,
    private val database: Database,
) {
    fun getRegion(id: Int): RegionDto? = repository.findByIdOrNull(id)?.let { toDto(it) }

    fun getRegions(): List<RegionDto> = repository.findAll().map { toDto(it) }

    @Transactional
    fun save(region: RegionDto): RegionDto {
        val entity =
            Region {
                this.name = region.name
            }
        val saved = repository.save(entity)
        return toDto(saved)
    }

    @Transactional
    fun modify(region: RegionDto) {
        region.id?.let { id ->
            val entity = database.regions.find { it.id eq id } ?: return
            entity.name = region.name
            entity.flushChanges()
        }
    }

    private fun toDto(entity: Region): RegionDto =
        RegionDto(
            id = entity.id,
            name = entity.name,
        )

    fun remove(id: Int): RegionDto? =
        repository.findByIdOrNull(id)?.let {
            repository.delete(it)
            toDto(it)
        }
}
