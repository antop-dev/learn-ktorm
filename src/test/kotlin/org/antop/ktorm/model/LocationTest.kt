package org.antop.ktorm.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.dsl.forEach
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@Rollback
class LocationTest(
    val database: Database,
) : StringSpec({

        "주소 조회" {
            val location =
                database.locations
                    .find { it.id eq 2000 }
            location shouldNotBeNull {
                id shouldBe 2000
                streetAddress shouldBe "40-5-12 Laogianggen"
                postalCode shouldBe "190518"
                city shouldBe "Beijing"
                stateProvince shouldBe null
                country.name shouldBe "China"
                country.region.name shouldBe "Asia"
            }
        }

        "주소 조회 DSL" {
            val location =
                database
                    .from(Locations)
                    .select()
                    .where { Locations.id eq 2000 }
                    .forEach {
                        println(it[Locations.city])
                    }
        }

        "지역이 아시아인 주소 조회" {
            val locations =
                database.locations
                    .filter { it.country.region.name eq "Asia" }
        }
    })
