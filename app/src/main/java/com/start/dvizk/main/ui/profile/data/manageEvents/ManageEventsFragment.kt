package com.start.dvizk.main.ui.profile.data.manageEvents

import ManageOrganizationAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.start.dvizk.R
import com.start.dvizk.create.organization.list.presentation.model.Organization
import com.start.dvizk.databinding.FragmentManageEventsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ManageEventsFragment : Fragment(R.layout.fragment_manage_events) {

    private val viewBinding: FragmentManageEventsBinding by viewBinding()
    private lateinit var adapter: ManageOrganizationAdapter
    private val viewModel: ManageEventsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initList()
    }

    private fun initViews() = with(viewBinding) {
        toolbar.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initList() = with(viewBinding) {
        organizationRecylcer.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = ManageOrganizationAdapter()
        val organization1 = Organization(1, "123", "kali", "1234", "124124", "dvsd", "", "", false)
        val organization2 = Organization(1, "123", "kali", "1234", "124124", "dvsd", "", "", false)
        adapter.setData(listOf(organization1, organization2))
        organizationRecylcer.adapter = adapter
    }
}
