package com.start.dvizk.registration.varification.presentation

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.start.dvizk.R
import com.start.dvizk.databinding.FragmentVerificationCodeBinding
import com.start.dvizk.util.ActivityLauncher
import org.koin.androidx.viewmodel.ext.android.viewModel


class VerificationCodeFragment :
		Fragment(R.layout.fragment_verification_code),
		OnClickListener
{
	private val binding: FragmentVerificationCodeBinding by viewBinding()
	private lateinit var fragment_registration_loader: View

	private val verificationViewModel: VerificationViewModel by viewModel<VerificationViewModel>()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initObservers()

		val sendAgainTextView: TextView = view.findViewById(R.id.fragment_verification_code_error_sending_code)
		val text = "Мне не пришло сообщение с кодом.\n Отправить ещё раз"
		val spannableString = SpannableString(text)
		val colorSpan = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.purple_500))
		spannableString.setSpan(colorSpan, 32, 51, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
		sendAgainTextView.text = spannableString

		fragment_registration_loader = requireActivity().findViewById(R.id.progress_bar)
		binding.fragmentVerificationCodeContinueButton.setOnClickListener(this)
	}

	override fun onClick(view: View?): Unit = with(binding) {
		when (view?.id) {
			fragmentVerificationCodeContinueButton.id -> {

				val code = fragmentVerificationCodeView.getCode()

				arguments?.getString("email")?.let {
					verificationViewModel.verify(
						it, code
					)
				}
			}

			fragmentVerificationCodeReturnButton.id -> {
				activity?.onBackPressed()
			}
		}
	}

	private fun initObservers() {
		verificationViewModel.verificationStateLiveData.observe(viewLifecycleOwner, ::handleState)
	}

	private fun handleState(state: VerificationState) {
		when (state) {
			is VerificationState.Failed -> {
				fragment_registration_loader.visibility = View.GONE
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is VerificationState.Loading -> {
				fragment_registration_loader.visibility = View.VISIBLE
			}
			is VerificationState.Success -> {
				fragment_registration_loader.visibility = View.GONE
				ActivityLauncher().startMainActivityWithFlags(requireContext())
			}
		}
	}
}