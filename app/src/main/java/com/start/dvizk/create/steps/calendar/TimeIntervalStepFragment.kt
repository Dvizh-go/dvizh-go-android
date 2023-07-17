package com.start.dvizk.create.steps.calendar

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.arch.EventCreateRouter
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.list.presentation.EVENT_ID_KEY
import com.start.dvizk.create.organization.list.presentation.SPECIFIC_DATA_KEY
import com.start.dvizk.create.organization.list.presentation.STEP_NUMBER_KEY
import com.start.dvizk.create.steps.calendar.model.EventDateTimeInterval
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.create.steps.data.model.StepDataApiResponse
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class TimeIntervalStepFragment : Fragment(), OnSelectTimeListener {

	private val viewModel: TimeIntervalStepViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var next: Button
	private lateinit var back: Button

	lateinit var adapter: EventDateTimeIntervalAdapter
	private var listDate = mutableListOf<String>()
	private var listTimeInterval = mutableListOf<EventDateTimeInterval>()
	private lateinit var timeIntervalRecyclerView: RecyclerView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_time_interval_step, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		viewModel.requestResponseStateLiveData.observe(viewLifecycleOwner, ::handleState)

	}

	private fun handleState(state: RequestResponseState) {
		when (state) {
			is RequestResponseState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is RequestResponseState.Loading -> {

			}
			is RequestResponseState.Success -> {
				val response = state.value as? StepDataApiResponse ?: return responseFailed()

				val ft: FragmentTransaction =
					requireActivity().supportFragmentManager.beginTransaction()
				val fragment = EventCreateRouter.getCreateStepFragment(response.data.nextStep.name)
				fragment.arguments = Bundle().apply {
					putInt(STEP_NUMBER_KEY, response.data.nextStep.numberStep)
					putInt(EVENT_ID_KEY, response.data.eventId)
				}
				ft.add(R.id.fragment_container, fragment)
				ft.addToBackStack(null)
				ft.commit()
			}
		}
	}

	private fun responseFailed() {
		Toast.makeText(requireContext(), "Ошибка сервера попробуйте позже", Toast.LENGTH_LONG)
			.show()
	}

	private fun initView(view: View) {

		timeIntervalRecyclerView = view.findViewById(R.id.fragment_time_interval_step_List)

		timeIntervalRecyclerView.layoutManager =
			LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		adapter = EventDateTimeIntervalAdapter(resources)
		adapter.setListener(this)
		timeIntervalRecyclerView.adapter = adapter

		arguments?.getStringArrayList(SPECIFIC_DATA_KEY)?.toMutableList()?.let {
			listDate.addAll(it)
			adapter.setData(mapListToTimeInterval(listDate))
		}
		next = view.findViewById(R.id.fragment_create_organization_next)
		back = view.findViewById(R.id.fragment_create_organization_back)

		next.setOnClickListener {
			arguments?.apply {
				viewModel.sendEventDate(
					token = sharedPreferencesRepository.getUserToken(),
					numberStep = getInt(STEP_NUMBER_KEY),
					eventId = getInt(EVENT_ID_KEY),
					dateList = listTimeInterval
				)
			}
		}

		back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
	}

	private fun mapListToTimeInterval(listDate: MutableList<String>): MutableList<EventDateTimeInterval> {
		listDate.forEach {
			listTimeInterval.add(EventDateTimeInterval(date = it, "", "", ""))
		}
		return listTimeInterval
	}

	override fun onStartTimeSelect(item: EventDateTimeInterval) {
		showTimePickerDialog(item, "start")
	}

	override fun onDurationTimeSelect(item: EventDateTimeInterval) {
		showTimePickerDialog(item, "duration")
	}

	private var selectedTime: Calendar = Calendar.getInstance()

	private fun showTimePickerDialog(item: EventDateTimeInterval, type: String) {
		val hour = selectedTime.get(Calendar.HOUR_OF_DAY)
		val minute = selectedTime.get(Calendar.MINUTE)
		var formattedTime = ""
		val timePickerDialog = TimePickerDialog(
			context,
			TimePickerDialog.OnTimeSetListener { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
				selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour)
				selectedTime.set(Calendar.MINUTE, selectedMinute)

				val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
				formattedTime = timeFormat.format(selectedTime.time)
				if (type == "start") {
					item.startTime = formattedTime
					adapter.notifyDataSetChanged()
				}
				if (type == "duration") {
					item.duration = formattedTime
					item.durationViewText = formatTimeToHoursMinutes(formattedTime)
					adapter.notifyDataSetChanged()
				}
			},
			hour,
			minute,
			DateFormat.is24HourFormat(activity)
		)

		timePickerDialog.show()

	}

	fun formatTimeToHoursMinutes(time: String): String {
		val parts = time.split(":")
		val hours = parts[0].toInt()
		val minutes = parts[1].toInt()

		val hoursLabel = if (hours > 0) {
			when {
				hours in 2..4 || hours in 22..24 -> "$hours часа"
				hours == 1 || hours == 21 -> "$hours час"
				else -> "$hours часов"
			}
		} else ""

		val minutesLabel = if (minutes > 0) {
			when {
				minutes in 2..4 || minutes in 22..24 || minutes in 32..34 || minutes in 42..44 || minutes in 52..54 -> "$minutes минуты"
				minutes == 1 || minutes == 21 || minutes == 31 || minutes == 41 || minutes == 51 -> "$minutes минута"
				else -> "$minutes минут"
			}
		} else ""

		return "$hoursLabel $minutesLabel"
	}
}