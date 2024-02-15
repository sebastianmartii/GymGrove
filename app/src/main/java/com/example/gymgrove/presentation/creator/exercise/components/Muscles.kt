package com.example.gymgrove.presentation.creator.exercise.components

interface Muscles

sealed interface UpperBody : Muscles {
    data object Chest : UpperBody
    data object Shoulders : UpperBody
    data object Triceps : UpperBody
    data object Biceps : UpperBody
    data object UpperBack : UpperBody
    data object Lats : UpperBody
    data object Forearms : UpperBody
}

sealed interface LowerBody : Muscles {
    data object Quads : LowerBody
    data object Hamstrings : LowerBody
    data object Glutes : LowerBody
    data object Calves : LowerBody
}

data object Unspecified : Muscles

fun Muscles.toMuscleString(): String {
    return when(this) {
        UpperBody.Chest -> "Chest"
        UpperBody.Triceps -> "Triceps"
        UpperBody.Shoulders -> "Shoulders"
        UpperBody.Biceps -> "Biceps"
        UpperBody.Forearms -> "Forearms"
        UpperBody.UpperBack -> "Upper Back"
        UpperBody.Lats -> "Lats"
        LowerBody.Quads -> "Quads"
        LowerBody.Hamstrings -> "Hamstrings"
        LowerBody.Glutes -> "Glutes"
        LowerBody.Calves -> "Calves"
        else -> "Unspecified"
    }
}

fun String.toMuscle(): Muscles {
    return when(this) {
        "Chest" -> UpperBody.Chest
        "Triceps" -> UpperBody.Triceps
        "Shoulders" -> UpperBody.Shoulders
        "Biceps" -> UpperBody.Biceps
        "Forearms" -> UpperBody.Forearms
        "Upper Back" -> UpperBody.UpperBack
        "Lats" -> UpperBody.Lats
        "Quads" -> LowerBody.Quads
        "Hamstrings" -> LowerBody.Hamstrings
        "Glutes" -> LowerBody.Glutes
        "Calves" -> LowerBody.Calves
        else -> Unspecified
    }
}