package com.start.eventgo.create.organization.create.presentation.model

sealed class OrganizationCreatingState {

    object Loading : OrganizationCreatingState()

    data class Failed(
        val message: String
    ) : OrganizationCreatingState()

    data class Success(
        val message: String
    ) : OrganizationCreatingState()
}
