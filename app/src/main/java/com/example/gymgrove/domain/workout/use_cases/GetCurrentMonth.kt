package com.example.gymgrove.domain.workout.use_cases

import com.example.gymgrove.domain.workout.model.CurrentMonth
import com.example.gymgrove.domain.workout.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.Calendar

class GetCurrentMonth(
    private val repo: WorkoutRepository,
    private val calendar: Calendar
) {
    fun subscribe(): Flow<CurrentMonth> {
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        return combine(
            repo.getCurrentMonthTotalWorkoutsDuration(currentMonth.toString()),
            repo.getCurrentMonthWorkoutsCount(currentMonth.toString())
        ) { duration, count ->
            CurrentMonth(
                duration = duration,
                count = count
            )
        }
    }
}