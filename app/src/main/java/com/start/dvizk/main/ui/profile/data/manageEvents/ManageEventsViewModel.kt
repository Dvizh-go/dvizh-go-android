package com.start.dvizk.main.ui.profile.data.manageEvents

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

class ManageEventsViewModel(
    application: Application,
    private val manageEventsRepository: ManageEventsRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main,
) : AndroidViewModel(application), CoroutineScope {

    val activeAvailableEventsLiveData: MutableLiveData<RequestResponseState> = MutableLiveData()

    fun getActiveAvailableEvents(organizationId: Int) {
        activeAvailableEventsLiveData.value = RequestResponseState.Loading

        launch(Dispatchers.IO) {
            val response = manageEventsRepository.getActiveAvailableEvents(organizationId)

            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        activeAvailableEventsLiveData.value =
                            RequestResponseState.Success(response.result)
                    is Response.Error ->
                        activeAvailableEventsLiveData.value =
                            RequestResponseState.Failed(response.error)
                }
            }
        }
    }
}
