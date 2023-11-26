package com.start.dvizk.create.steps.bottomsheet

import com.start.dvizk.main.ui.home.presentation.model.Category

interface OnSelectCategoryBottomSheetDismiss {
    fun onDismissGetCategoryList(list: List<Category>)
}
