package com.example.gymgrove.data.local.exercise

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = false)
    val exerciseId: Int? = null,
    val workoutId: String,
    val name: String,
    val totalSets: Int,
    val totalRepsPerSet: Int,
    val dateString: String,
    // optional
    val inBetweenSetsRestTimeInSeconds: Int? = null,
    val tempo: Int? = null,
    val workingSets: Int? = null,
    val warmUpSets: Int? = null,
    val partialReps: Int? = null,
    val repsInReserve: Int? = null,
)
