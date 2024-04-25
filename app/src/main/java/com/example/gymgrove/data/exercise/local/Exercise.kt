package com.example.gymgrove.data.exercise.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Int? = null,
    val workoutId: Int,
    val name: String,
    val sets: Int,
    val totalRepsPerSet: Int,
    val dateString: String,
    val workingWeight: Float,
    // optional
    val shortDescription: String? = null,
    val inBetweenSetsRestTimeInSeconds: Int? = null,
    val eccentric: Int? = null,
    val pauseAtBottom: Int? = null,
    val concentric: Int? = null,
    val pauseAtTop: Int? = null,
    val warmUpSets: Int? = null,
    val partialReps: Int? = null,
    val repsInReserve: Int? = null,
    // drop set info, warm up sets info, anything not applicable above but important
    val additionalInfo: String? = null
)
