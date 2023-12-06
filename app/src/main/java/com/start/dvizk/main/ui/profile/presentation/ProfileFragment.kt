package com.start.dvizk.main.ui.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.databinding.FragmentProfilePageBinding
import com.start.dvizk.main.ui.home.presentation.HomeFragment
import com.start.dvizk.main.ui.profile.data.model.ProfileDataModel
import com.start.dvizk.util.ActivityLauncher
import com.start.dvizk.util.FormName
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val viewBinding: FragmentProfilePageBinding by viewBinding()

    private val viewModel: ProfileViewModel by viewModel()
    private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()

        viewModel.getUserProfile(sharedPreferencesRepository.getUserToken())
    }

    private fun initView() {
        Glide.with(this)
            .load(sharedPreferencesRepository.getUserImage())
            .placeholder(R.drawable.ic_logo)
            .apply(RequestOptions.circleCropTransform())
            .into(viewBinding.fragmentProfilePageProfileAvatar)

        viewBinding.fragmentProfilePageCreateEvent.setOnClickListener {
            ActivityLauncher().startCreateActivity(requireContext())
        }

        viewBinding.fragmentProfilePageLogout.setOnClickListener {
            val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

            sharedPreferencesRepository.clearAll()
            ft.replace(R.id.nav_host_fragment_activity_main, HomeFragment())
            ft.commit()
        }

        viewBinding.fragmentProfilePageManagingMyEvents.setOnClickListener {
            ActivityLauncher().startBaseActivity(requireContext(), FormName().MANAGE_ORGANIZATION, Bundle())
//            val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
//
//            ft.replace(R.id.nav_host_fragment_activity_main, ManageEventsFragment())
//            ft.addToBackStack(null)
//            ft.commit()
        }
    }

    private fun initObserver() {
        viewModel.profileStateLiveData.observe(viewLifecycleOwner, ::handleState)
    }

    private fun handleState(state: RequestResponseState) = with(viewBinding) {
        when (state) {
            is RequestResponseState.Failed -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
            }
            is RequestResponseState.Loading -> {
            }
            is RequestResponseState.Success -> {
                val response = state.value as? ProfileDataModel ?: return

                fragmentProfilePageProfileName.text = response.name

// 				fragmentProfilePageEventsCount.text = response.eventsCount.toString()
// 				fragmentProfilePageFollowersCount.text = response.subscribers.toString()
// 				fragmentProfilePageSubscriptionsCount.text = response.subscriptions.toString()
            }
        }
    }
}
