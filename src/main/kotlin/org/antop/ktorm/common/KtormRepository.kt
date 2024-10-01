package org.antop.ktorm.common

import org.springframework.data.repository.ListCrudRepository
import org.springframework.data.repository.findByIdOrNull

interface KtormRepository<T : Any, ID : Any> : ListCrudRepository<T, ID> {
    override fun existsById(id: ID): Boolean = findByIdOrNull(id) != null

    override fun deleteAllById(ids: MutableIterable<ID>) {
        ids.forEach { deleteById(it) }
    }

    override fun deleteAll(entities: Iterable<T>) {
        entities.forEach { delete(it) }
    }
}
