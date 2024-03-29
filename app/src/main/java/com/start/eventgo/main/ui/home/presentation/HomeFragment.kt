package com.start.eventgo.main.ui.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.start.eventgo.R
import com.start.eventgo.arch.data.SharedPreferencesRepository
import com.start.eventgo.main.ui.detail.presentation.EventDetailsFragment
import com.start.eventgo.main.ui.home.presentation.model.CategoriesListState
import com.start.eventgo.main.ui.home.presentation.model.Category
import com.start.eventgo.main.ui.home.presentation.model.Event
import com.start.eventgo.main.ui.home.presentation.model.FirstItemMarginDecoration
import com.start.eventgo.main.ui.home.presentation.model.PopularEventsState
import com.start.eventgo.main.ui.home.presentation.model.UpcomingEventsState
import com.start.eventgo.util.CacheUtil
import com.start.eventgo.util.Constant
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val EVENT_ID = "event_id"

class HomeFragment : Fragment(), OnItemClickListener, OnCategoryItemClickListener {

    private val viewModel: HomeViewModel by viewModel()
    private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

    private lateinit var popularRecyclerView: RecyclerView
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var upcomingEventsRecyclerView: RecyclerView
    private lateinit var fragment_home_upcoming_events_progress_bar: ProgressBar
    private lateinit var fragment_home_upcoming_title_show: TextView
    private lateinit var fragment_home_popular_title_show: TextView

    private lateinit var popularAdapter: BigEventAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var defaultEventAdapter: DefaultEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        initPopularList()
        initObserver()
        viewModel.getPopularEvents()
        viewModel.getCategories()
        viewModel.getUpcomingEvents(1, 0)

        // Просто чтобы из логов взять данные
        println(
            "Token " + sharedPreferencesRepository.getUserToken() + "\n" +
                "UserId " + sharedPreferencesRepository.getUserId()
        )

        checkPageContents()
    }

    private fun checkPageContents() {
        if (CacheUtil().getBooleanValueByKey(requireContext(), Constant().RETURN_EVENT_DETAILS)) {
            openEventDetails(CacheUtil().getIntValueByKey(requireContext(), Constant().EVENT_DATA_VALUE))
        }
    }

    private fun openEventDetails(eventId: Int) {
        val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val fragment = EventDetailsFragment()
        fragment.arguments = Bundle().apply {
            putInt(EVENT_ID, eventId)
        }
        CacheUtil().saveBooleanValue(requireContext(), Constant().RETURN_EVENT_DETAILS, false)
        ft.add(R.id.nav_host_fragment_activity_main, fragment)
        ft.addToBackStack("")
        ft.commit()
    }

    override fun onItemClick(data: Event) {
        openEventDetails(data.id.toInt())
    }

    private fun initView(view: View) {
        fragment_home_upcoming_title_show = view.findViewById(R.id.fragment_home_upcoming_title_show)
        fragment_home_popular_title_show = view.findViewById(R.id.fragment_home_popular_title_show)
        popularRecyclerView = view.findViewById(R.id.fragment_home_popular)
        categoryRecyclerView = view.findViewById(R.id.fragment_home_category_recycler_view)
        upcomingEventsRecyclerView = view.findViewById(R.id.fragment_home_upcoming_events_recycler_view)
        fragment_home_upcoming_events_progress_bar = view.findViewById(R.id.fragment_home_upcoming_events_progress_bar)

        fragment_home_upcoming_title_show.setOnClickListener {
            val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

            ft.add(R.id.nav_host_fragment_activity_main, AllUpcomingEventsFragment())
            ft.addToBackStack("")
            ft.commit()
        }

        fragment_home_popular_title_show.setOnClickListener {
            val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            ft.add(R.id.nav_host_fragment_activity_main, AllUpcomingEventsFragment())
            ft.addToBackStack("")
            ft.commit()
        }
    }

    private fun initPopularList() {
        popularRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popularAdapter = BigEventAdapter(requireContext(), resources)
        popularAdapter.setListener(this)
        popularRecyclerView.adapter = popularAdapter

        categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryAdapter(resources)
        categoryAdapter.setListener(this)
        categoryRecyclerView.adapter = categoryAdapter
        val firstItemOffset = resources.getDimensionPixelSize(R.dimen.first_item_offset_main_event)
        val subsequentItemOffset = resources.getDimensionPixelSize(R.dimen.subsequent_item_offset)
        val itemDecoration = FirstItemMarginDecoration(firstItemOffset, subsequentItemOffset)
        categoryRecyclerView.addItemDecoration(itemDecoration)

        upcomingEventsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        defaultEventAdapter = DefaultEventAdapter(requireContext(), resources)
        defaultEventAdapter.setListener(this)
        upcomingEventsRecyclerView.adapter = defaultEventAdapter
    }

    private fun initObserver() {
        viewModel.popularEventsStateLiveData.observe(viewLifecycleOwner, ::popularListInit)
        viewModel.categoriesListState.observe(viewLifecycleOwner, ::categoriesListInit)
        viewModel.upcomingEventsStateLiveData.observe(viewLifecycleOwner, ::upcomingListInit)
    }

    private fun popularListInit(state: PopularEventsState) {
        when (state) {
            is PopularEventsState.Failed -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
            }
            is PopularEventsState.Loading -> {
            }
            is PopularEventsState.Success -> {
                popularAdapter.setData(state.events)
            }
        }
    }

    private fun categoriesListInit(state: CategoriesListState) {
        when (state) {
            is CategoriesListState.Failed -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
            }
            is CategoriesListState.Loading -> {
            }
            is CategoriesListState.Success -> {
                checkEmptiness(state.categories)
            }
        }
    }

    private fun checkEmptiness(categories: List<Category>) {
        categoryAdapter.setData(categories.filter { !it.isEmpty })
    }

    private fun upcomingListInit(state: UpcomingEventsState) {
        when (state) {
            is UpcomingEventsState.Failed -> {
                fragment_home_upcoming_events_progress_bar.visibility = View.GONE
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
            }
            is UpcomingEventsState.Loading -> {
                fragment_home_upcoming_events_progress_bar.visibility = View.VISIBLE
            }
            is UpcomingEventsState.Success -> {
                fragment_home_upcoming_events_progress_bar.visibility = View.GONE
                defaultEventAdapter.setData(state.events)
            }
        }
    }

    override fun onItemClick(data: Category) {
        viewModel.getUpcomingEvents(page = 1, categoryId = data.id.toInt())
    }

    companion object {
        fun getInstance(args: Bundle?): HomeFragment {
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
