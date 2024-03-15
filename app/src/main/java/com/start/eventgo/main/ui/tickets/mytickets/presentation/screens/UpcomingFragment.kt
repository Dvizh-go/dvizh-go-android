package com.start.eventgo.main.ui.tickets.mytickets.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.start.eventgo.R
import com.start.eventgo.arch.data.SharedPreferencesRepository
import com.start.eventgo.auth.main.MainAuthFragment
import com.start.eventgo.main.ui.tickets.mytickets.data.model.MyTicket
import com.start.eventgo.main.ui.tickets.mytickets.data.model.state.UpcomingTicketsState
import com.start.eventgo.main.ui.tickets.mytickets.presentation.MyTicketsViewModel
import com.start.eventgo.main.ui.tickets.mytickets.presentation.adapter.UpcomingTicketsAdapter
import com.start.eventgo.main.ui.tickets.ticket.presentation.OnTicketClickListener
import com.start.eventgo.main.ui.tickets.ticket.presentation.TICKET_ID
import com.start.eventgo.main.ui.tickets.ticket.presentation.TicketFragment
import com.start.eventgo.main.ui.tickets.ticket.presentation.cancel.CancelTicketFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpcomingFragment : Fragment(), OnTicketClickListener {

    private val viewModel: MyTicketsViewModel by viewModel()
    private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

    private var list = mutableListOf<MyTicket>()

    private lateinit var fragment_my_tickets_upcoming_recycler: RecyclerView
    private lateinit var fragment_my_tickets_upcoming_empty: ConstraintLayout

    private lateinit var fragment_my_tickets_upcoming_progressbar: ProgressBar

    private lateinit var upcomingTicketsLayoutManager: LinearLayoutManager
    private lateinit var upcomingTicketsAdapter: UpcomingTicketsAdapter

    private var page = 1
    private var totalPage: Int = 1
    private var loading = true
    private var pastVisibleItems = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_my_tickets_upcoming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        initList(view)
        initObserver()

        viewModel.getUserUpcomingTickets(page, sharedPreferencesRepository.getUserToken())
    }

    override fun onViewTicket(data: MyTicket) {
        val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val fragment = TicketFragment()
        fragment.arguments = Bundle().apply {
            putInt(TICKET_ID, data.id)
        }
        ft.add(R.id.nav_host_fragment_activity_main, fragment)
        ft.addToBackStack("")
        ft.commit()
    }

    override fun onCancelTicket(data: MyTicket) {
        val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val fragment = CancelTicketFragment()
        fragment.arguments = Bundle().apply {
            putInt(TICKET_ID, data.id)
        }
        ft.add(R.id.nav_host_fragment_activity_main, fragment)
        ft.addToBackStack("")
        ft.commit()
    }

    private fun initView(view: View) {
        fragment_my_tickets_upcoming_empty =
            view.findViewById(R.id.fragment_my_tickets_upcoming_empty)
        fragment_my_tickets_upcoming_progressbar =
            view.findViewById(R.id.fragment_my_tickets_upcoming_progressbar)
    }

    private fun initList(view: View) {
        fragment_my_tickets_upcoming_recycler =
            view.findViewById(R.id.fragment_my_tickets_upcoming_recycler)

        upcomingTicketsLayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        upcomingTicketsAdapter = UpcomingTicketsAdapter(resources)
        upcomingTicketsAdapter.setListener(this)

        fragment_my_tickets_upcoming_recycler.apply {
            layoutManager = upcomingTicketsLayoutManager
            adapter = upcomingTicketsAdapter
        }
    }

    private fun initObserver() {
        viewModel.userUpcomingTicketsStateLiveData.observe(viewLifecycleOwner, ::handleState)
    }

    private fun handleState(state: UpcomingTicketsState) {
        when (state) {
            is UpcomingTicketsState.Failed -> {
                Toast.makeText(requireContext(), "Для доступа к этой функции необходимо войти в свой аккаунт", Toast.LENGTH_SHORT).show()
                val navView = activity?.findViewById(R.id.nav_view) as? BottomNavigationView
                navView?.selectedItemId = R.id.navigation_profile
                val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                ft.replace(R.id.nav_host_fragment_activity_main, MainAuthFragment())
                ft.commit()
            }
            is UpcomingTicketsState.Loading -> {
            }
            is UpcomingTicketsState.Success -> {
                totalPage = state.totalPage
// 				setupPagination(totalPage)
                val myUpcomingTickets = state.upcomingTickets
                list.addAll(myUpcomingTickets)

                if (myUpcomingTickets.isNotEmpty()) {
                    fragment_my_tickets_upcoming_empty.visibility = View.GONE
                    fragment_my_tickets_upcoming_recycler.visibility = View.VISIBLE
                    upcomingTicketsAdapter.setData(list)
                } else {
                    fragment_my_tickets_upcoming_recycler.visibility = View.GONE
                    fragment_my_tickets_upcoming_empty.visibility = View.VISIBLE
                }

                loading = true
            }
        }
    }

    private fun setupPagination(totalPage: Int) {
        fragment_my_tickets_upcoming_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = upcomingTicketsLayoutManager.childCount
                    totalItemCount = upcomingTicketsLayoutManager.itemCount
                    pastVisibleItems = upcomingTicketsLayoutManager.findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            loading = false
                            if (page != totalPage) {
                                viewModel.getUserUpcomingTickets(++page, sharedPreferencesRepository.getUserToken())
                            }
                        }
                    }
                }
            }
        })
    }
}
