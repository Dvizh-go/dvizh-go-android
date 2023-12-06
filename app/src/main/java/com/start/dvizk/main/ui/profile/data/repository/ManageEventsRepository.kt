package com.start.dvizk.main.ui.profile.data.repository

import com.start.dvizk.create.organization.list.presentation.model.OrganizationList
import com.start.dvizk.main.ui.profile.data.api.ManageEventsApi
import com.start.dvizk.main.ui.profile.data.model.AvailableEvents
import com.start.dvizk.main.ui.profile.data.model.EventUsers
import com.start.dvizk.network.Response
import org.json.JSONObject

class ManageEventsRepository(val manageEventsApi: ManageEventsApi) : EventsRepository {

    override fun getAvailableOrganizations(user_id: Int): Response<OrganizationList, String> {
        val response = manageEventsApi
            .getAvailableOrganizations(
                userId = user_id
            ).execute()

        if (response.isSuccessful) return Response.Success(response.body()!!)
        val message = JSONObject(response.errorBody()?.string()!!).getString("message")
        return Response.Error(message)
    }

    override fun getActiveAvailableEvents(organizationId: Int): Response<AvailableEvents, String> {
        val response = manageEventsApi.getActiveAvailableEvents(
            organizationId = organizationId
        ).execute()

        if (response.isSuccessful) return Response.Success(response.body()!!)
//        val message = JSONObject(response.errorBody().toString()).getString("message")
        return Response.Error(response.message())
    }

    override fun getAvailableEventUsers(datetimeId: Int, page: Int): Response<List<EventUsers>, String> {
        val response = manageEventsApi.getAvailableEventUsers(
            datetimeId = datetimeId,
            page = page
        ).execute()

        if (response.isSuccessful) return Response.Success(response.body()!!)
        val message = JSONObject(response.errorBody()?.string()!!).getString("message")
        return Response.Error(message)
    }
}
