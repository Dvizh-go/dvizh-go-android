package com.start.eventgo.search.search.presentation.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener
import com.start.eventgo.R
import com.start.eventgo.search.search.presentation.SelectedParams
import com.start.eventgo.search.search.presentation.model.DateRange
import java.util.Calendar

class CalendarFragment : Fragment() {

    private lateinit var fragment_search_calendar_view: MaterialCalendarView
    private var listener: SelectedParams? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar_search, container, false)
    }

    override fun onResume() {
        super.onResume()
        view?.requestLayout()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_search_calendar_view = view.findViewById(R.id.fragment_search_calendar_view)

        initCalendar(view)
    }

    fun initCalendar(view: View) {
        fragment_search_calendar_view = view.findViewById(R.id.fragment_search_calendar_view)

        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val minDate = CalendarDay.from(year, month, day)
        fragment_search_calendar_view.state().edit()
            .setMinimumDate(minDate)
            .commit()

        fragment_search_calendar_view.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE)

        fragment_search_calendar_view.setHeaderTextAppearance(R.style.CalendarHeader)
        fragment_search_calendar_view.setWeekDayTextAppearance(R.style.CalendarWeekDay)
        fragment_search_calendar_view.setDateTextAppearance(R.style.CalendarDate)
        fragment_search_calendar_view.selectionColor = R.color.purple_200

        // Устанавливаем слушатель выбора интервала дат
        fragment_search_calendar_view.setOnRangeSelectedListener(object : OnRangeSelectedListener {
            override fun onRangeSelected(widget: MaterialCalendarView, dates: List<CalendarDay>) {
                if (dates.size >= 2) {
                    listener?.onDateRangeSelected(
                        DateRange(
                            from = "" + dates.first().year + "-" + dates.first().month + "-" + dates.first().day,
                            to = "" + dates.last().year + "-" + dates.last().month + "-" + dates.last().day,
                        )
                    )
                    println(dates.first().toString() + "------" + dates.last().toString())
                    // Обработка выбора интервала дат
                    // Вы можете использовать startDate и endDate для дальнейшей обработки
                }
            }
        })
    }

    fun setListener(listenerDateRange: SelectedParams?) {
        listener = listenerDateRange
    }
}
