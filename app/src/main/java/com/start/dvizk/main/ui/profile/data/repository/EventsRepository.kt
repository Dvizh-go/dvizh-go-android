package com.start.dvizk.main.ui.profile.data.repository

import com.start.dvizk.create.organization.list.presentation.model.OrganizationList
import com.start.dvizk.main.ui.profile.data.model.AvailableEvents
import com.start.dvizk.main.ui.profile.data.model.EventUsers
import com.start.dvizk.network.Response

interface EventsRepository {

    fun getAvailableOrganizations(
        user_id: Int
    ): Response<OrganizationList, String>

    fun getActiveAvailableEvents(
        organizationId: Int
    ): Response<AvailableEvents, String>

    fun getAvailableEventUsers(
        datetimeId: Int,
        page: Int
    ): Response<List<EventUsers>, String>
}
