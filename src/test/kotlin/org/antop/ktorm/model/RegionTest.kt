package org.antop.ktorm.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@Rollback
class RegionTest(
    val database: Database,
) : StringSpec({

        "새로운 지역 저장" {
            val entity =
                Region {
                    id = 5
                    name = "Oceania"
                }
            database.regions.add(entity)

            val region = database.regions.find { it.id eq 5 }
            region shouldNotBeNull {
                this.id shouldBeEqual 5
                this.name shouldBeEqual "Oceania"
            }
        }
    })
