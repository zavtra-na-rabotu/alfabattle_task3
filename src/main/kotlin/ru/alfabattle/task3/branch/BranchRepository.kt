package ru.alfabattle.task3.branch

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import ru.alfabattle.task3.models.tables.Branches.BRANCHES

@Repository
class BranchRepository(private val dsl: DSLContext) {
    fun findBranchById(id: Int): Branch? {
        return dsl.select()
            .from(BRANCHES)
            .where(BRANCHES.ID.eq(id))
            .fetchOneInto(Branch::class.java)
    }

    fun findAll(): List<Branch> {
        return dsl.select()
            .from(BRANCHES)
            .fetchInto(Branch::class.java)
    }
}