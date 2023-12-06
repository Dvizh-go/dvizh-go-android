package com.start.eventgo.main.ui.profile.data.repository

import com.start.eventgo.create.organization.list.presentation.model.OrganizationList
import com.start.eventgo.main.ui.profile.data.model.AvailableEvents
import com.start.eventgo.main.ui.profile.data.model.EventUsers
import com.start.eventgo.network.Response

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
