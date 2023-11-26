package com.start.dvizk.main.ui.profile.data.manageEvents

import ManageOrganizationAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.list.presentation.model.OrganizationList
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.databinding.FragmentManageEventsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageEventsFragment : Fragment(R.layout.fragment_manage_events) {

    private val viewBinding: FragmentManageEventsBinding by viewBinding()
    private lateinit var adapter: ManageOrganizationAdapter
    private val viewModel: ManageEventsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAvailableOrganizations(
            SharedPreferencesRepository(requireContext()).getUserId().toInt()
        )
        initViews()
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.availableOrganizationsLiveData.observe(viewLifecycleOwner, ::handleList)
    }

    private fun handleList(requestResponseState: RequestResponseState) {
        when (requestResponseState) {
            is RequestResponseState.Failed -> {
                Snackbar.make(
                    viewBinding.toolbar,
                    requestResponseState.message,
                    Snackbar.LENGTH_LONG
                ).show()
            }
            is RequestResponseState.Loading -> {
            }
            is RequestResponseState.Success -> {
                val response = requestResponseState.value
                initList(response)
            }
        }
    }

    private fun initViews() = with(viewBinding) {
        toolbar.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initList(listValue: Any) = with(viewBinding) {
        organizationRecylcer.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = ManageOrganizationAdapter()
        adapter.setData(listValue as OrganizationList)
        organizationRecylcer.adapter = adapter
    }
}
