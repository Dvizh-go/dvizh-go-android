package com.start.dvizk.main.ui.profile.data.api

import com.start.dvizk.create.organization.list.presentation.model.OrganizationList
import com.start.dvizk.main.ui.profile.data.model.AvailableEvents
import com.start.dvizk.main.ui.profile.data.model.EventUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ManageEventsApi {

    @GET("/api/v2/organization")
    fun getAvailableOrganizations(
        @Query("user_id") userId: Int
    ): Call<OrganizationList>

    @GET("/api/v2/organization/{organizationID}/events/active")
    fun getActiveAvailableEvents(
        @Path("organizationID") organizationId: Int
    ): Call<List<AvailableEvents>>

    @GET("/api/v2/event_datetime/{datetimeId}/members")
    fun getAvailableEventUsers(
        @Path("datetimeId") datetimeId: Int,
        @Query("page") page: Int
    ): Call<List<EventUsers>>
}
