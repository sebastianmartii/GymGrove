package com.example.gymgrove.data.exercise

import com.example.gymgrove.domain.exercise.model.Tempo
import com.example.gymgrove.data.exercise.local.Exercise as ExerciseEntity
import com.example.gymgrove.data.exercise.local.SavedExercise as SavedExerciseEntity
import com.example.gymgrove.domain.exercise.model.Exercise as ExerciseModel
import com.example.gymgrove.domain.exercise.model.SavedExercise as SavedExerciseModel

fun SavedExerciseEntity.toSavedExerciseModel(): SavedExerciseModel {
    return SavedExerciseModel(
        id = savedExerciseId!!,
        name = savedExerciseName,
        primaryTargetMuscle = primaryTargetMuscle,
        secondaryTargetMuscle = secondaryTargetMuscle,
        additionalInfo = additionalInfo
    )
}

fun ExerciseModel.toExerciseEntity(): ExerciseEntity {
    return ExerciseEntity(
        exerciseId = id,
        workoutId = workoutId,
        name = name,
        shortDescription = shortDescription,
        sets = sets,
        totalRepsPerSet = totalRepsPerSet,
        dateString = dateString,
        workingWeight = workingWeight,
        inBetweenSetsRestTimeInSeconds = inBetweenSetsRestTimeInSeconds,
        eccentric = tempo?.eccentric,
        pauseAtBottom = tempo?.pauseAtBottom,
        concentric = tempo?.concentric,
        pauseAtTop = tempo?.pauseAtTop,
        warmUpSets = warmUpSets,
        partialReps = partialReps,
        repsInReserve = repsInReserve,
        additionalInfo = additionalInfo
    )
}

fun ExerciseEntity.toExerciseModel(): ExerciseModel {
    val isTempoNull = concentric == null || eccentric == null
            || pauseAtBottom == null || pauseAtTop == null
    return ExerciseModel(
        id = exerciseId!!,
        workoutId = workoutId,
        name = name,
        shortDescription = shortDescription,
        sets = sets,
        totalRepsPerSet = totalRepsPerSet,
        dateString = dateString,
        workingWeight = workingWeight,
        inBetweenSetsRestTimeInSeconds = inBetweenSetsRestTimeInSeconds,
        tempo = if (isTempoNull) null else Tempo(
            eccentric = eccentric!!,
            pauseAtBottom = pauseAtBottom!!,
            concentric = concentric!!,
            pauseAtTop = pauseAtTop!!
        ),
        warmUpSets = warmUpSets,
        partialReps = partialReps,
        repsInReserve = repsInReserve,
        additionalInfo = additionalInfo
    )
}