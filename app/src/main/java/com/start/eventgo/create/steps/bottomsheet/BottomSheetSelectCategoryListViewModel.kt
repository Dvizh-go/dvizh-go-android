package com.start.eventgo.create.steps.bottomsheet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.start.eventgo.arch.CustomMutableLiveData
import com.start.eventgo.main.ui.home.data.HomePageRepository
import com.start.eventgo.main.ui.home.presentation.model.CategoriesListState
import com.start.eventgo.main.ui.home.presentation.model.Category
import com.start.eventgo.network.Response
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BottomSheetSelectCategoryListViewModel(
    private val homePageRepository: HomePageRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.Main
) : ViewModel(),
    CoroutineScope,
    OnCategoryCheckItemClickListener {

    val categoriesListState: MutableLiveData<CategoriesListState> = CustomMutableLiveData()
    val navigationValueLiveData: MutableLiveData<Navigation> = CustomMutableLiveData()

    private var isSubCategoryList = false
    val categoryList = mutableListOf<Category>()

    fun getCategories(parent_id: Int?) {
        categoriesListState.value = CategoriesListState.Loading

        launch(Dispatchers.IO) {
            val response = homePageRepository.getCategoriesWithOutAll(parent_id)

            launch(Dispatchers.Main) {
                when (response) {
                    is Response.Success ->
                        categoriesListState.value =
                            CategoriesListState.Success(response.result)
                    is Response.Error ->
                        categoriesListState.value =
                            CategoriesListState.Failed(response.error)
                }
            }
        }
    }

    override fun onItemClick(data: Category) {

//        if (data.is_sub) {
//            getCategories(data.id.toInt())
//            isSubCategoryList = true
//
//            return
//        }

        if (categoryList.contains(data)) {
            categoryList.remove(data)
        } else {
            categoryList.add(data)
        }
    }

    fun navigateBack() {
//        if (isSubCategoryList) {
//            isSubCategoryList = false
//            navigation.value = Navigation.OnSubCategoryBack
//        } else {
        navigationValueLiveData.value = Navigation.OnCategoryBack
//        }
    }
}
