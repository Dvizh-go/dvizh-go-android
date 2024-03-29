package com.start.eventgo.main.ui.home.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.eventgo.arch.CustomMutableLiveData
import com.start.eventgo.main.ui.home.data.HomePageRepository
import com.start.eventgo.main.ui.home.presentation.model.CategoriesListState
import com.start.eventgo.main.ui.home.presentation.model.PopularEventsState
import com.start.eventgo.main.ui.home.presentation.model.UpcomingEventsState
import com.start.eventgo.network.Response
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homePageRepository: HomePageRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope {

    val popularEventsStateLiveData: MutableLiveData<PopularEventsState> = CustomMutableLiveData()
    val upcomingEventsStateLiveData: MutableLiveData<UpcomingEventsState> = CustomMutableLiveData()
    val categoriesListState: MutableLiveData<CategoriesListState> = CustomMutableLiveData()

    fun getPopularEvents() {
        launch(Dispatchers.IO) {
            val response = homePageRepository.getPopularEvents(1)

            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        popularEventsStateLiveData.value =
                            PopularEventsState.Success(response.result)
                    is Response.Error ->
                        popularEventsStateLiveData.value =
                            PopularEventsState.Failed(response.error.toString())
                }
            }
        }
    }

    fun getCategories() {
        launch(Dispatchers.IO) {
            val response = homePageRepository.getCategories(null)

            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        categoriesListState.value =
                            CategoriesListState.Success(response.result)
                    is Response.Error ->
                        categoriesListState.value =
                            CategoriesListState.Failed(response.error.toString())
                }
            }
        }
    }

    fun getUpcomingEvents(
        page: Int,
        categoryId: Int,
    ) {
        upcomingEventsStateLiveData.value = UpcomingEventsState.Loading
        launch(Dispatchers.IO) {
            val response = homePageRepository.getUpcomingEvents(page = page, categoryId = categoryId)

            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        upcomingEventsStateLiveData.value =
                            UpcomingEventsState.Success(response.result, 0)
                    is Response.Error ->
                        upcomingEventsStateLiveData.value =
                            UpcomingEventsState.Failed(response.error.toString())
                }
            }
        }
    }
}
