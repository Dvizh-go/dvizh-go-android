package com.start.eventgo.create.steps.category

import com.start.eventgo.main.ui.home.presentation.model.Category

interface OnCategoryListDeleteItem {
    fun onDelete(item: Category)
}
