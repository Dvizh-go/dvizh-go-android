package com.start.eventgo.create.organization.list.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.eventgo.arch.CustomMutableLiveData
import com.start.eventgo.create.organization.create.presentation.model.CurrentStepState
import com.start.eventgo.create.organization.list.data.OrganizationRepository
import com.start.eventgo.create.organization.list.presentation.adapter.OnOrganizationItemClickListener
import com.start.eventgo.create.organization.list.presentation.model.Organization
import com.start.eventgo.create.organization.list.presentation.model.OrganizationListState
import com.start.eventgo.network.Response
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrganizationsListViewModel(
    private val organizationRepository: OrganizationRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope,
    OnOrganizationItemClickListener {

    val organizationListStateLiveData: MutableLiveData<OrganizationListState> = CustomMutableLiveData()
    val currentStepStateLiveData: MutableLiveData<CurrentStepState> = CustomMutableLiveData()

    var organizationId: Int = 0

    fun getOrganizationList(userId: Int) {
        launch(Dispatchers.IO) {
            val response = organizationRepository.getOrganizationList(userId)

            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        organizationListStateLiveData.value =
                            OrganizationListState.Success(response.result)
                    is Response.Error ->
                        organizationListStateLiveData.value =
                            OrganizationListState.Failed(response.error)
                }
            }
        }
    }

    fun getCurrentStep(token: String) {
        launch(Dispatchers.IO) {
            val response = organizationRepository.getCurrentStep(
                token = token,
                organizationId = organizationId
            )
            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        currentStepStateLiveData.value =
                            CurrentStepState.Success(response.result)
                    is Response.Error ->
                        currentStepStateLiveData.value =
                            CurrentStepState.Failed(response.error)
                }
            }
        }
    }

    override fun onItemClick(data: Organization) {
        organizationId = data.id
        Log.i("userToken", organizationId.toString())
    }
}
