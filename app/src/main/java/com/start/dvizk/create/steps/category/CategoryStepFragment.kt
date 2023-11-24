package com.start.dvizk.create.steps.category

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.start.dvizk.R
import com.start.dvizk.arch.EventCreateRouter
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.list.presentation.EVENT_ID_KEY
import com.start.dvizk.create.organization.list.presentation.STEP_NUMBER_KEY
import com.start.dvizk.create.steps.bottomsheet.BottomSheetSelectCategoryListFragment
import com.start.dvizk.create.steps.bottomsheet.OnSelectCategoryBottomSheetDismiss
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.create.steps.data.model.StepDataApiResponse
import com.start.dvizk.databinding.FragmentCategoryStepBinding
import com.start.dvizk.main.ui.home.presentation.model.Category
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryStepFragment : Fragment(R.layout.fragment_category_step), OnSelectCategoryBottomSheetDismiss, OnCategoryListDeleteItem {

    private val viewModel: CategoryStepViewModel by viewModel()
    private val binding: FragmentCategoryStepBinding by viewBinding()
    private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

    private lateinit var defaultEventAdapter: CreateEventCategoryListAdapter
    private var categoryList = mutableListOf<Category>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        viewModel.requestSendCatResponseStateLiveData.observe(viewLifecycleOwner, ::handleState)
    }

    private fun initView() = with(binding) {
        fragmentCreateOrganizationBackImage.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        fragmentCategoryStepList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        defaultEventAdapter = CreateEventCategoryListAdapter(resources)
        defaultEventAdapter.setListener(this@CategoryStepFragment)
        fragmentCategoryStepList.adapter = defaultEventAdapter

        fragmentCreateOrganizationNext.setOnClickListener {
            arguments?.apply {

                val ids = categoryList.map { it.id.toInt() }

                viewModel.sendEventCat(
                    token = sharedPreferencesRepository.getUserToken(),
                    numberStep = getInt(STEP_NUMBER_KEY),
                    eventId = getInt(EVENT_ID_KEY),
                    catList = ids
                )
            }
        }

        fragmentCreateOrganizationBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        fragmentCreateOrganizationButton.setOnClickListener {
            val bottomSheetFragment = BottomSheetSelectCategoryListFragment()
            bottomSheetFragment.setListener(this@CategoryStepFragment)
            bottomSheetFragment.show(parentFragmentManager, "MyBottomSheetFragmentTag")
        }
    }

    override fun onDismissGetCategoryList(list: List<Category>) {
        categoryList.addAll(list.toMutableList())
        categoryList.distinctBy { it.id }
        defaultEventAdapter.setData(categoryList)
        defaultEventAdapter.notifyDataSetChanged()
    }

    override fun onDelete(item: Category) {
        categoryList.remove(item)
        defaultEventAdapter.setData(categoryList)
        defaultEventAdapter.notifyDataSetChanged()
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
                val imm: InputMethodManager =
                    context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
                val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
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
        Toast.makeText(requireContext(), "Ошибка сервера попробуйте позже", Toast.LENGTH_LONG).show()
    }
}
