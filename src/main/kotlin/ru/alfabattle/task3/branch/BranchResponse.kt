package ru.alfabattle.task3.branch

data class BranchResponse(
    val id: Int,
    val title: String,
    val lon: Double,
    val lat: Double,
    val address: String,
    val distance: Int
)

data class ErrorResponse(
    val status: String
)