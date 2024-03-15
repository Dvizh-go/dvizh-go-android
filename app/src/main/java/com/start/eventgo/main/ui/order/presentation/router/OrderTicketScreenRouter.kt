package com.start.eventgo.main.ui.order.presentation.router

import androidx.fragment.app.Fragment
import com.start.eventgo.main.ui.order.presentation.steps.CommandDataStepFragment
import com.start.eventgo.main.ui.order.presentation.steps.ContactDataStepFragment
import com.start.eventgo.main.ui.order.presentation.steps.TicketsCountStepFragment

object OrderTicketScreenRouter {

    fun getTicketOrderStepFragment(stepName: String?): Fragment = when (stepName) {
        "userData" -> {
            ContactDataStepFragment()
        }
        "teamData" -> {
            CommandDataStepFragment()
        }
        "choiceTickets" -> {
            TicketsCountStepFragment()
        }
        else -> {
            TicketsCountStepFragment()
        }
    }
}
