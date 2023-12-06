package com.start.eventgo.main.ui.profile.data.manageEvents

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.start.eventgo.R
import com.start.eventgo.create.steps.data.model.RequestResponseState
import com.start.eventgo.databinding.FragmentManageEventsBinding
import com.start.eventgo.util.Constant
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageEventsFragment : Fragment(R.layout.fragment_manage_events) {

    private val viewBinding: FragmentManageEventsBinding by viewBinding()
    private val viewModel: ManageEventsViewModel by viewModel()
    private lateinit var adapter: ManageEventsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireActivity().intent.getBundleExtra(Constant().FORM_DATA)
        viewModel.getActiveAvailableEvents(bundle?.getInt(Constant().ORGANIZATION_ID) ?: 0)
        bindViewmodel()
    }

    private fun bindViewmodel() {
        viewModel.activeAvailableEventsLiveData.observe(viewLifecycleOwner, ::handleList)
    }

    private fun handleList(requestResponseState: RequestResponseState) {
        when (requestResponseState) {
            is RequestResponseState.Failed -> {
                Snackbar.make(
                    viewBinding.eventsRecycler,
                    requestResponseState.message,
                    Snackbar.LENGTH_LONG
                ).show()
                viewBinding.eventsProgressBar.isVisible = false
            }
            is RequestResponseState.Loading -> {
                viewBinding.eventsProgressBar.isVisible = true
            }
            is RequestResponseState.Success -> {
                val response = requestResponseState.value
//                initList(response)
                Log.i("responseState", response.toString())
                viewBinding.eventsProgressBar.isVisible = false
            }
        }
    }

    private fun initList(response: Any) = with(viewBinding) {
//        eventsRecycler.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        adapter = ManageEventsAdapter(::onItemClick)
//        adapter.setData(listValue as OrganizationList)
//        eventsRecycler.adapter = adapter
    }

//    private fun onItemClick(organization: Organization) {
//        Log.i()
//    }

    companion object {
        fun getInstance(args: Bundle?): ManageEventsFragment {
            val fragment = ManageEventsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
