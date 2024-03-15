package com.start.eventgo.search.search.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.eventgo.arch.CustomMutableLiveData
import com.start.eventgo.main.ui.home.data.HomePageRepository
import com.start.eventgo.main.ui.home.presentation.model.CategoriesListState
import com.start.eventgo.main.ui.home.presentation.model.UpcomingEventsState
import com.start.eventgo.network.Response
import com.start.eventgo.search.search.presentation.model.DateRange
import com.start.eventgo.search.search.presentation.model.TicketQuantities
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val homePageRepository: HomePageRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope {

    val categoriesListState: MutableLiveData<CategoriesListState> = CustomMutableLiveData()
    val upcomingEventsStateLiveData: MutableLiveData<UpcomingEventsState> = CustomMutableLiveData()

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

    fun getSearchedEvents(
        token: String?,
        categories: List<Int>?,
        page: Int?,
        months: List<Int> = emptyList(),
        ticketQuantities: TicketQuantities?,
        dateRange: DateRange? = null
    ) {
        upcomingEventsStateLiveData.value = UpcomingEventsState.Loading
        launch(Dispatchers.IO) {
            val response = homePageRepository.getSearchedEvents(
                dateRange = dateRange,
                token = token,
                categories = categories,
                page = page,
                months = months.ifEmpty { null },
                ticketQuantities = ticketQuantities
            )

            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        upcomingEventsStateLiveData.value =
                            UpcomingEventsState.Success(response.result.events, response.result.nbTotal)
                    is Response.Error ->
                        upcomingEventsStateLiveData.value =
                            UpcomingEventsState.Failed(response.error.toString())
                }
            }
        }
    }
}
