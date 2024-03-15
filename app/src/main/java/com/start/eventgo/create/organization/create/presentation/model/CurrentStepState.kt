package com.start.eventgo.create.organization.create.presentation.model

import com.start.eventgo.create.organization.list.presentation.model.CurrentStep

sealed class CurrentStepState {

    object Loading : CurrentStepState()

    data class Failed(
        val message: String
    ) : CurrentStepState()

    data class Success(
        val step: CurrentStep
    ) : CurrentStepState()
}
