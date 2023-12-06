package com.start.dvizk.main.ui.profile.data.manageEvents.organizations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.main.ui.profile.data.repository.ManageEventsRepository
import com.start.dvizk.network.Response
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ManageOrganizationViewModel(
    application: Application,
    val manageEventsRepository: ManageEventsRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : AndroidViewModel(application),
    CoroutineScope {

    val availableOrganizationsLiveData: MutableLiveData<RequestResponseState> = MutableLiveData()
    val availableEventUsersLiveData: MutableLiveData<RequestResponseState> = MutableLiveData()

    fun getAvailableOrganizations(userId: Int) {
        availableOrganizationsLiveData.value = RequestResponseState.Loading

        launch(Dispatchers.IO) {
            val response = manageEventsRepository.getAvailableOrganizations(userId)

            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        availableOrganizationsLiveData.value =
                            RequestResponseState.Success(response.result)
                    is Response.Error ->
                        availableOrganizationsLiveData.value =
                            RequestResponseState.Failed(response.error)
                }
            }
        }
    }

    fun getAvailableEventUsers(datetimeId: Int, page: Int) {
        availableEventUsersLiveData.value = RequestResponseState.Loading

        launch(Dispatchers.IO) {
            val response = manageEventsRepository.getAvailableEventUsers(datetimeId, page)

            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        availableEventUsersLiveData.value =
                            RequestResponseState.Success(response.result)
                    is Response.Error ->
                        availableEventUsersLiveData.value =
                            RequestResponseState.Failed(response.error)
                }
            }
        }
    }
}
