package com.start.eventgo.search.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.start.eventgo.R
import com.start.eventgo.main.ui.detail.presentation.EventDetailsFragment
import com.start.eventgo.main.ui.home.presentation.EVENT_ID
import com.start.eventgo.main.ui.home.presentation.OnItemClickListener
import com.start.eventgo.main.ui.home.presentation.model.Event
import com.start.eventgo.search.search.adapter.SearchEventAdapter
import com.start.eventgo.util.CacheUtil
import com.start.eventgo.util.Constant

class SearchListFragment : Fragment(), OnItemClickListener {

    private lateinit var search_result_list_header_back: ImageView

    private lateinit var fragment_favorites_events_recycler_view: RecyclerView
    private lateinit var fragment_favorites_subtitle: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search_result_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_result_list_header_back = view.findViewById(R.id.search_result_list_header_back)
        search_result_list_header_back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        fragment_favorites_subtitle = view.findViewById(R.id.fragment_favorites_subtitle)
        fragment_favorites_events_recycler_view = view.findViewById(R.id.fragment_favorites_events_recycler_view)
        fragment_favorites_events_recycler_view.layoutManager = LinearLayoutManager(view.context)

        val dataList = arguments?.getParcelableArrayList<Event>("SEARCH_RESULT_LIST")
        val total = arguments?.getInt("SEARCH_INT")

        fragment_favorites_subtitle.text = "Найдено: " + total

        val favoriteEventAdapter = dataList?.toMutableList()
            ?.let { SearchEventAdapter(it, resources) }
        favoriteEventAdapter?.setListener(this)
        fragment_favorites_events_recycler_view.adapter = favoriteEventAdapter
    }

    private fun openEventDetails(eventId: Int) {
        val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val fragment = EventDetailsFragment()
        fragment.arguments = Bundle().apply {
            putInt(EVENT_ID, eventId)
        }
        CacheUtil().saveBooleanValue(requireContext(), Constant().RETURN_EVENT_DETAILS, false)
        ft.add(R.id.fragment_container, fragment)
        ft.addToBackStack("")
        ft.commit()
    }

    override fun onItemClick(data: Event) {
        Log.i("openEvent", data.id.toString())
        openEventDetails(data.id.toInt())
    }
}
