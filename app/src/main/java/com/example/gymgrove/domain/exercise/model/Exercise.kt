package com.example.gymgrove.domain.exercise.model

data class Exercise(
    val name: String,
    val shortDescription: String,
    val totalSets: Int,
    val totalRepsPerSet: Int,
    // optional
    val inBetweenSetsRestTimeInSeconds: Int? = null,
    val tempo: Tempo? = null,
    val workingSets: Int? = null,
    val warmUpSets: Int? = null,
    val partialReps: Int? = null,
    val repsInReserve: Int? = null,
)
