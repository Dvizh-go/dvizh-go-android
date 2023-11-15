package com.start.dvizk.registration.createpassword.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textfield.TextInputEditText
import com.start.dvizk.R
import com.start.dvizk.databinding.FragmentPasswordGenerationBinding
import com.start.dvizk.registration.registr.presentation.model.User
import com.start.dvizk.registration.varification.presentation.VerificationCodeFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class PasswordGenerationFragment :
    Fragment(R.layout.fragment_password_generation),
    OnClickListener {

    private val passwordGenerationViewModel: PasswordGenerationViewModel by viewModel()
    private val binding: FragmentPasswordGenerationBinding by viewBinding()

    private lateinit var fragment_registration_loader: View

    private var email: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        fragment_registration_loader = requireActivity().findViewById(R.id.progress_bar)
        binding.fragmentPasswordGenerationContinueButton.setOnClickListener(this)
        binding.fragmentPasswordGenerationReturnButton.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    override fun onClick(view: View?): Unit = with(binding) {
        when (view?.id) {
			fragmentPasswordGenerationContinueButton.id -> {
                arguments?.apply {
                    val user: User? = getParcelable("user_regis")

                    if (user == null) {
                        Toast.makeText(
                            requireContext(),
                            "Данные заполнены некорректно",
                            Toast.LENGTH_LONG
                        ).show()

                        return@apply
                    }

                    email = user.email

                    if (fragmentPasswordGenerationEditText1.text.toString()
                        == fragmentPasswordGenerationEditText1.text.toString()
                    ) {
                        passwordGenerationViewModel.register(
                            email = user.email,
                            password = fragmentPasswordGenerationEditText2.text.toString(),
                            name = user.name,
                            nickname = user.nickname,
                            phone_number = user.phone_number,
                            gender = user.gender,
                            birthday = user.birthday,
                            image = getMultipart(user.image),
                        )
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Пароли не совпадают",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun initObservers() {
        passwordGenerationViewModel.passwordGenerationStateLiveData.observe(
            viewLifecycleOwner,
            ::handleState
        )
    }

    private fun handleState(state: PasswordGenerationState) {
        when (state) {
            is PasswordGenerationState.Failed -> {
                fragment_registration_loader.visibility = View.GONE
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
            }
            is PasswordGenerationState.Loading -> {
                fragment_registration_loader.visibility = View.VISIBLE
            }
            is PasswordGenerationState.Success -> {
                fragment_registration_loader.visibility = View.GONE
                val bundle = Bundle().apply {
                    putString("email", email)
                }
                val ft: FragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                val fragment = VerificationCodeFragment()
                fragment.arguments = bundle
                ft.add(R.id.nav_host_fragment_activity_main, fragment)
                ft.addToBackStack(null)
                ft.commit()
            }
        }
    }

    private fun getMultipart(filePath: String?): MultipartBody.Part? {
        if (filePath.isNullOrEmpty()) {

            return null
        }
        val file = File(filePath)

        val body = RequestBody.create("image/*".toMediaTypeOrNull(), file)

        return MultipartBody.Part.createFormData("image", file.name, body)
    }
}