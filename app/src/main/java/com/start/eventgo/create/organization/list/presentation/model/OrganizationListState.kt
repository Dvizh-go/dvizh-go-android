package com.start.eventgo.create.organization.list.presentation.model

sealed class OrganizationListState {

    object Loading : OrganizationListState()

    data class Failed(
        val message: String
    ) : OrganizationListState()

    data class Success(
        val organizations: MutableList<Organization>
    ) : OrganizationListState()
}
