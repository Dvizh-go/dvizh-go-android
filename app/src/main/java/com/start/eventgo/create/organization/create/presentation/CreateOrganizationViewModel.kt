package com.start.eventgo.create.organization.create.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.eventgo.arch.CustomMutableLiveData
import com.start.eventgo.create.organization.create.data.CreateOrganizationRepository
import com.start.eventgo.create.organization.create.presentation.model.OrganizationCreatingState
import com.start.eventgo.network.Response
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class CreateOrganizationViewModel(
    private val createOrganizationRepository: CreateOrganizationRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope {

    val organizationCreatingState: MutableLiveData<OrganizationCreatingState> = CustomMutableLiveData()

    fun createOrganization(
        token: String,
        name: String,
        description: String,
        phone_number: String,
        instagram: String,
        whatsapp: String,
        email: String,
        image: MultipartBody.Part?,
    ) {
        organizationCreatingState.value = OrganizationCreatingState.Loading
        launch(Dispatchers.IO) {
            val response = createOrganizationRepository.createOrganization(
                token = token,
                name = name,
                description = description,
                phone_number = phone_number,
                instagram = instagram,
                whatsapp = whatsapp.ifEmpty { null },
                email = email.ifEmpty { null },
                image = image,
            )

            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        organizationCreatingState.value =
                            OrganizationCreatingState.Success(response.result)
                    is Response.Error ->
                        organizationCreatingState.value =
                            OrganizationCreatingState.Failed(response.error.toString())
                }
            }
        }
    }
}
