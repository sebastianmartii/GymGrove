package com.example.gymgrove.domain.exercise.model

data class Exercise(
    val id: Int,
    val workoutId: Int,
    val name: String,
    val sets: Int,
    val totalRepsPerSet: Int,
    val dateString: String,
    val workingWeight: Float,
    // optional
    val shortDescription: String? = null,
    val inBetweenSetsRestTimeInSeconds: Int? = null,
    val tempo: Tempo? = null,
    val warmUpSets: Int? = null,
    val partialReps: Int? = null,
    val repsInReserve: Int? = null,
    // drop set info, warm up sets info, anything not applicable above but important
    val additionalInfo: String? = null
)
