package ru.alfabattle.task3.branch

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/branches")
class BranchController(
    private val branchService: BranchService
) {

    @GetMapping("/{branchId}")
    fun findBranchById(@PathVariable branchId: Int):ResponseEntity<*> {
        val branch = branchService.findBranchById(branchId)
            ?: return ResponseEntity(ErrorResponse("branch not found"), NOT_FOUND)

        return ResponseEntity(branch, HttpStatus.OK)
    }

    @GetMapping()
    fun findNearest(
        @RequestParam(value = "lat", required = true) lat: Double,
        @RequestParam(value = "lon", required = true) lon: Double
    ): ResponseEntity<*> {
        val branch = branchService.findNearest(lat, lon)
            ?: return ResponseEntity(ErrorResponse(status = "atm not found"), NOT_FOUND)

        val response = BranchResponse(
            id = branch.id,
            title = branch.title,
            lon = branch.lon,
            lat = branch.lat,
            address = branch.address,
            distance = branchService.distance(branch.lat, branch.lon, lat, lon).toInt()
        )

        return ResponseEntity(response, HttpStatus.OK)
    }
}