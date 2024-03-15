package com.start.eventgo.main.ui.profile.data.manageEvents.organizations

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.start.eventgo.R
import com.start.eventgo.arch.data.SharedPreferencesRepository
import com.start.eventgo.create.organization.list.presentation.model.Organization
import com.start.eventgo.create.organization.list.presentation.model.OrganizationList
import com.start.eventgo.create.steps.data.model.RequestResponseState
import com.start.eventgo.databinding.FragmentOrganizationEventsBinding
import com.start.eventgo.util.ActivityLauncher
import com.start.eventgo.util.Constant
import com.start.eventgo.util.FormName
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageOrganizationsFragment : Fragment(R.layout.fragment_organization_events) {

    private val viewBinding: FragmentOrganizationEventsBinding by viewBinding()
    private lateinit var adapter: ManageOrganizationAdapter
    private val viewModel: ManageOrganizationViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAvailableOrganizations(
            SharedPreferencesRepository(requireContext()).getUserId().toInt()
        )

        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.availableOrganizationsLiveData.observe(viewLifecycleOwner, ::handleList)
    }

    private fun handleList(requestResponseState: RequestResponseState) {
        when (requestResponseState) {
            is RequestResponseState.Failed -> {
                Snackbar.make(
                    viewBinding.organizationRecylcer,
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
                initList(response)
                viewBinding.eventsProgressBar.isVisible = false
            }
        }
    }

    private fun initList(listValue: Any) = with(viewBinding) {
        organizationRecylcer.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = ManageOrganizationAdapter(::onItemClick)
        adapter.setData(listValue as OrganizationList)
        organizationRecylcer.adapter = adapter
    }

    private fun onItemClick(organization: Organization) {
        val bundle = Bundle()
        bundle.putInt(Constant().ORGANIZATION_ID, organization.id)
        ActivityLauncher().startBaseActivity(requireContext(), FormName().MANAGE_EVENTS, bundle)
    }

    companion object {
        fun getInstance(args: Bundle?): ManageOrganizationsFragment {
            val fragment = ManageOrganizationsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
