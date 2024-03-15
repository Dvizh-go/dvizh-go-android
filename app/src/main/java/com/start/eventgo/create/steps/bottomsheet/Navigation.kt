package com.start.eventgo.create.steps.bottomsheet

sealed class Navigation {

    object OnSubCategoryBack : Navigation()

    object OnCategoryBack : Navigation()
}
