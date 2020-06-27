package ru.alfabattle.task3.branch

import org.springframework.stereotype.Service
import ru.alfabattle.task3.extensions.toRad
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Service
class BranchService(
    private val branchRepository: BranchRepository
) {
    fun findBranchById(id: Int): Branch? {
        return branchRepository.findBranchById(id)
    }

    fun findNearest(lat: Double, lon: Double): Branch? {
        val allBranches = branchRepository.findAll()

        return allBranches
            .minBy { distance(it.lat, it.lon, lat, lon) }
    }

    fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371000.0
        val latDist = (lat2 - lat1).toRad() / 2
        val lonDist = (lon2 - lon1).toRad() / 2
        val a = sin(latDist) * sin(latDist) + cos(lat1.toRad()) * cos(lat2.toRad()) * sin(lonDist) * sin(lonDist)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadius * c
    }
}