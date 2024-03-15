package com.start.eventgo.arch

import androidx.fragment.app.Fragment
import com.start.eventgo.create.dialog.SuccessDialog
import com.start.eventgo.create.steps.about.AboutStepFragment
import com.start.eventgo.create.steps.booking.BookingStepFragment
import com.start.eventgo.create.steps.calendar.CalendarStepFragment
import com.start.eventgo.create.steps.cancelrule.CancelRuleStepFragment
import com.start.eventgo.create.steps.category.CategoryStepFragment
import com.start.eventgo.create.steps.classification.ClassificationStepFragment
import com.start.eventgo.create.steps.eventrule.EventRuleStepFragment
import com.start.eventgo.create.steps.freeorpay.FreeOrPayStepFragment
import com.start.eventgo.create.steps.guestcount.GusetCountStepFragment
import com.start.eventgo.create.steps.language.LanguageStepFragment
import com.start.eventgo.create.steps.location.LocationStepFragment
import com.start.eventgo.create.steps.name.NameStepFragment
import com.start.eventgo.create.steps.needings.NeededItemsStepFragment
import com.start.eventgo.create.steps.photo.PhotoStepFragment
import com.start.eventgo.create.steps.price.PriceStepFragment
import com.start.eventgo.create.steps.service.AdditionalServiceStepFragment
import com.start.eventgo.create.steps.teamcount.TeamCountStepFragment
import com.start.eventgo.create.steps.type.presentation.TypeStepFragment
import com.start.eventgo.create.steps.visitperson.AllowedGuestStepFragment

object EventCreateRouter {

    fun getCreateStepFragment(stepName: String?): Fragment = when (stepName) {
        "type" -> {
            TypeStepFragment()
        }
        "language" -> {
            LanguageStepFragment()
        }
        "category" -> {
            CategoryStepFragment()
        }
        "description" -> {
            AboutStepFragment()
        }
        "regionAndLocation" -> {
            LocationStepFragment()
        }
        "datetime" -> {
            CalendarStepFragment()
        }
        "purchaseDeadline" -> {
            BookingStepFragment()
        }
        "additionalServices" -> {
            AdditionalServiceStepFragment()
        }
        "necessaryItems" -> {
            NeededItemsStepFragment()
        }
        "requirement" -> {
            AllowedGuestStepFragment()
        }
        "images" -> {
            PhotoStepFragment()
        }
        "name" -> {
            NameStepFragment()
        }
        "classification" -> {
            ClassificationStepFragment()
        }
        "maximumGroupSize" -> {
            GusetCountStepFragment()
        }
        "entryCondition" -> {
            FreeOrPayStepFragment()
        }
        "price" -> {
            PriceStepFragment()
        }
        "cancellationRules" -> {
            CancelRuleStepFragment()
        }
        "rules" -> {
            EventRuleStepFragment()
        }
        "numberOfTeams" -> {
            TeamCountStepFragment()
        }
        "datetimeSingle", "datetimeGroup" -> {
            CalendarStepFragment()
        }
        "success" -> {
            SuccessDialog()
        }
        else -> {
            TypeStepFragment()
        }
    }
}
