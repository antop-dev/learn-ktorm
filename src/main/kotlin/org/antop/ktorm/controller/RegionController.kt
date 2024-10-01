package org.antop.ktorm.controller

import org.antop.ktorm.dto.RegionDto
import org.antop.ktorm.service.RegionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/regions")
class RegionController(
    private val service: RegionService,
) {
    @GetMapping("/{id:[0-9]+}")
    fun get(
        @PathVariable id: Int,
    ): ResponseEntity<RegionDto> =
        service
            .getRegion(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @GetMapping
    fun list(): List<RegionDto> = service.getRegions()

    @PostMapping
    fun save(
        @RequestBody region: RegionDto,
    ): ResponseEntity<RegionDto> {
        val saved = service.save(region)
        val location =
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.id)
                .toUri()
        return ResponseEntity.created(location).body(saved)
    }

    @PutMapping
    fun modify(
        @RequestBody region: RegionDto,
    ): RegionDto {
        service.modify(region)
        return region
    }

    @DeleteMapping("/{id:[0-9]+}")
    fun remove(
        @PathVariable id: Int,
    ): ResponseEntity<RegionDto> =
        service
            .remove(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
}
