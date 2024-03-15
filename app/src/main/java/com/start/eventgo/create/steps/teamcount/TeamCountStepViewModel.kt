package com.start.eventgo.create.steps.teamcount

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.eventgo.arch.CustomMutableLiveData
import com.start.eventgo.create.steps.data.EventCreateRepository
import com.start.eventgo.create.steps.data.model.RequestResponseState
import com.start.eventgo.network.Response
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamCountStepViewModel(
    private val eventCreateRepository: EventCreateRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope {

    val requestTeamCountResponseStateLiveData: MutableLiveData<RequestResponseState> = CustomMutableLiveData()

    fun sendTeamCount(
        token: String,
        numberStep: Int,
        maximum_number_teams: Int,
        maximum_number_participants_team: Int,
        eventId: Int,
    ) {
        requestTeamCountResponseStateLiveData.value = RequestResponseState.Loading
        launch(Dispatchers.IO) {
            val response = eventCreateRepository.sendTeamCount(
                token = token,
                numberStep = numberStep,
                maximum_number_participants_team = maximum_number_participants_team,
                maximum_number_teams = maximum_number_teams,
                eventId = eventId,
            )
            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        requestTeamCountResponseStateLiveData.value =
                            RequestResponseState.Success(response.result)
                    is Response.Error ->
                        requestTeamCountResponseStateLiveData.value =
                            RequestResponseState.Failed(response.error.toString())
                }
            }
        }
    }
}
