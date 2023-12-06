package com.start.eventgo.create.steps.bottomsheet

import com.start.eventgo.main.ui.home.presentation.model.Category

interface OnSelectCategoryBottomSheetDismiss {
    fun onDismissGetCategoryList(list: List<Category>)
}
