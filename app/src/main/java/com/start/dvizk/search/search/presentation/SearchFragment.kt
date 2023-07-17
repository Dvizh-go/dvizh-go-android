package com.start.dvizk.search.search.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.main.ui.home.presentation.HomeViewModel
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.FirstItemMarginDecoration
import com.start.dvizk.main.ui.tickets.adapter.PagerAdapter
import com.start.dvizk.search.search.adapter.SearchCalendarPagerAdapter
import com.start.dvizk.search.search.adapter.SearchCategoryAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SearchFragment :
		Fragment(),
		OnClickListener,
		SearchCategoryItemClick
{

	private val viewModel: SearchViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var fragment_bottom_sheet_category: View
	private lateinit var fragment_home_search_edit_text: EditText
	private lateinit var fragment_search_calendar_header: View
	private lateinit var fragment_search_category_list: RecyclerView

	private lateinit var fragment_search_calendar_next: Button
	private lateinit var fragment_create_organization_separator_1: View
	private lateinit var fragment_search_quests_adult_plus: View
	private lateinit var fragment_search_quests_stud_plus: View
	private lateinit var vieq: View
	private lateinit var fragment_search_calendar_clear: TextView
	private lateinit var fragment_search_quests_stud_minus: View
	private lateinit var fragment_search_quests_adult_minus: View
	private lateinit var fragment_search_quests_stud_count: TextView
	private lateinit var fragment_search_quests_adult_count: TextView
	private lateinit var fragment_search_quests_header: TextView


	private lateinit var view_quest: View

	private lateinit var fragment_search_calendar_pager: ViewPager2
	private lateinit var fragment_search_calendar_tabs: TabLayout

	private lateinit var categoryAdapter: SearchCategoryAdapter

	private var adultCount = 0
	private var childCount = 0

	override fun onCreateView(
			inflater: LayoutInflater, container: ViewGroup?,
			savedInstanceState: Bundle?
	): View? {

		return inflater.inflate(R.layout.fragment_search, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fragment_search_calendar_header = view.findViewById(R.id.fragment_search_calendar_header)
		fragment_home_search_edit_text = view.findViewById(R.id.fragment_home_search_edit_text)
		fragment_bottom_sheet_category = view.findViewById(R.id.fragment_bottom_sheet_category)
		fragment_search_category_list = view.findViewById(R.id.fragment_search_category_list)
		fragment_search_calendar_next = view.findViewById(R.id.fragment_search_calendar_next)
		fragment_create_organization_separator_1 = view.findViewById(R.id.fragment_create_organization_separator_1)
		fragment_search_calendar_clear = view.findViewById(R.id.fragment_search_calendar_clear)
		vieq = view.findViewById(R.id.vieq)
		fragment_search_quests_header = view.findViewById(R.id.fragment_search_quests_header)

		fragment_search_quests_stud_plus = view.findViewById(R.id.fragment_search_quests_stud_plus)
		fragment_search_quests_adult_plus = view.findViewById(R.id.fragment_search_quests_adult_plus)
		fragment_search_quests_stud_minus = view.findViewById(R.id.fragment_search_quests_stud_minus)
		fragment_search_quests_adult_minus = view.findViewById(R.id.fragment_search_quests_adult_minus)
		fragment_search_quests_stud_count = view.findViewById(R.id.fragment_search_quests_stud_count)
		fragment_search_quests_adult_count = view.findViewById(R.id.fragment_search_quests_adult_count)

		view_quest = view.findViewById(R.id.view_quest)

		fragment_search_calendar_pager = view.findViewById(R.id.fragment_search_calendar_pager)
		val adapter = SearchCalendarPagerAdapter(this)
		fragment_search_calendar_pager.adapter = adapter

		val tabTitles = listOf("Выбрать даты", "Я гибкий")

		fragment_search_calendar_tabs = view.findViewById(R.id.fragment_search_calendar_tabs)
		TabLayoutMediator(fragment_search_calendar_tabs, fragment_search_calendar_pager) { tab, position ->
			tab.text = tabTitles[position]
		}.attach()

		fragment_search_calendar_pager.post {
			val viewPagerHeight = fragment_search_calendar_pager.height
			for (i in 0 until fragment_search_calendar_pager.childCount) {
				val child = fragment_search_calendar_pager.getChildAt(i)
				child.minimumHeight = viewPagerHeight
			}
		}

		fragment_search_category_list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		categoryAdapter = SearchCategoryAdapter(resources)
		categoryAdapter.setListener(this)
		fragment_search_category_list.adapter = categoryAdapter
		val firstItemOffset = resources.getDimensionPixelSize(R.dimen.first_item_offset)
		val subsequentItemOffset = resources.getDimensionPixelSize(R.dimen.subsequent_item_offset)
		val itemDecoration = FirstItemMarginDecoration(firstItemOffset, subsequentItemOffset)
		fragment_search_category_list.addItemDecoration(itemDecoration)

		var cats = mutableListOf<Category>(
			Category(1,0,"Все","http://161.35.145.58/images/event_category/1676537107.jpg",  false),
			Category(2,0,"Музыка","http://161.35.145.58/images/event_category/1676878543.jpg",  false),
			Category(3,0,"Спорт","http://161.35.145.58/images/event_category/1676878543.jpg",  false),
		)
		categoryAdapter.setData(cats)

		fragment_bottom_sheet_category.setOnClickListener(this)
		fragment_search_calendar_header.setOnClickListener(this)
		fragment_search_calendar_next.setOnClickListener(this)
		fragment_search_quests_adult_plus.setOnClickListener(this)
		fragment_search_quests_stud_plus.setOnClickListener(this)
		fragment_search_quests_stud_minus.setOnClickListener(this)
		fragment_search_quests_adult_minus.setOnClickListener(this)
		fragment_search_quests_header.setOnClickListener(this)

		fragment_home_search_edit_text.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
				// Ничего не делаем
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				// Вызываем фильтрацию списка при изменении текста в EditText
				val filteredList = cats.filter { (it.name.contains(s.toString(), ignoreCase = true)) }
				categoryAdapter.setData(filteredList)
				categoryAdapter.notifyDataSetChanged()
			}

			override fun afterTextChanged(s: Editable?) {
				// Ничего не делаем
			}
		})
	}

	override fun onClick(view: View?) {
		when (view?.id) {
			fragment_bottom_sheet_category.id -> {

				fragment_home_search_edit_text.visibility = View.VISIBLE
				fragment_search_category_list.visibility = View.VISIBLE

				vieq.visibility = View.GONE
				view_quest.visibility = View.GONE
				fragment_create_organization_separator_1.visibility = View.GONE
				fragment_search_calendar_clear.visibility = View.GONE
				fragment_search_calendar_next.visibility = View.GONE
			}
			fragment_search_calendar_header.id -> {
				fragment_home_search_edit_text.visibility = View.GONE
				fragment_search_category_list.visibility = View.GONE
				view_quest.visibility = View.GONE

				vieq.visibility = View.VISIBLE
				fragment_create_organization_separator_1.visibility = View.VISIBLE
				fragment_search_calendar_clear.visibility = View.VISIBLE
				fragment_search_calendar_next.visibility = View.VISIBLE
			}
			fragment_search_calendar_next.id -> {

				vieq.visibility = View.GONE
				fragment_create_organization_separator_1.visibility = View.GONE
				fragment_search_calendar_clear.visibility = View.GONE
				fragment_search_calendar_next.visibility = View.GONE
				view_quest.visibility = View.VISIBLE
			}
			fragment_search_quests_adult_plus.id -> {
				adultCount++
				fragment_search_quests_adult_count.text = adultCount.toString()
			}
			fragment_search_quests_stud_plus.id -> {
				childCount++
				fragment_search_quests_stud_count.text = childCount.toString()
			}
			fragment_search_quests_adult_minus.id -> {
				if (adultCount == 0) return
				adultCount--
				fragment_search_quests_adult_count.text = adultCount.toString()
			}
			fragment_search_quests_stud_minus.id -> {
				if (childCount == 0) return
				childCount--
				fragment_search_quests_stud_count.text = childCount.toString()
			}
			fragment_search_quests_header.id -> {
				fragment_home_search_edit_text.visibility = View.GONE
				fragment_search_category_list.visibility = View.GONE
				view_quest.visibility = View.GONE
				vieq.visibility = View.GONE
				fragment_create_organization_separator_1.visibility = View.GONE
				fragment_search_calendar_clear.visibility = View.GONE
				fragment_search_calendar_next.visibility = View.GONE
				view_quest.visibility = View.VISIBLE
			}
		}
	}

	override fun onItemClick(category: Category) {

	}
}