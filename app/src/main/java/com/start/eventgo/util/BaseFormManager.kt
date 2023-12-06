package com.start.eventgo.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.start.eventgo.main.ui.home.presentation.HomeFragment
import com.start.eventgo.main.ui.profile.data.manageEvents.ManageEventsFragment
import com.start.eventgo.main.ui.profile.data.manageEvents.organizations.ManageOrganizationsFragment

object BaseFormManager {

    fun getStartFormByName(formName: String?, formArgs: Bundle?): Fragment {

        formArgs?.putString(Constant().FORM_NAME, formName)

        return when (formName) {
            FormName().MANAGE_EVENTS -> {
                ManageEventsFragment.getInstance(formArgs)
            }
            FormName().MANAGE_ORGANIZATION -> {
                ManageOrganizationsFragment.getInstance(formArgs)
            }
            else -> {
                HomeFragment.getInstance(formArgs)
            }
        }
    }
}
