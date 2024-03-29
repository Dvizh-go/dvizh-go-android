package com.start.eventgo.auth.profile

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.start.eventgo.R
import com.start.eventgo.arch.data.SharedPreferencesRepository
import com.start.eventgo.network.RetrofitClient
import com.start.eventgo.registration.registr.presentation.RegistrationFragment
import com.start.eventgo.registration.registr.presentation.model.User
import com.start.eventgo.util.ActivityLauncher
import com.start.eventgo.util.CacheUtil
import com.start.eventgo.util.Constant
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileAuthFragment : Fragment(), OnClickListener {

    private var isPasswordVisible = false

    val sharedPreferencesRepository: SharedPreferencesRepository by inject()

    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registrationButton: TextView
    private lateinit var showPasswordImageView: ImageView
    private lateinit var authLoginBtn: Button
    private lateinit var returnButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginEditText = view.findViewById(R.id.fragment_auth_login_edit_text)
        passwordEditText = view.findViewById(R.id.fragment_auth_password_edit_text)
        registrationButton = view.findViewById(R.id.fragment_auth_registration_text)
        showPasswordImageView = view.findViewById(R.id.fragment_auth_password_show)
        authLoginBtn = view.findViewById(R.id.fragment_auth_login)
        returnButton = view.findViewById(R.id.fragment_auth_return_button)

        registrationButton.setOnClickListener {
            val ft: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            ft.add(R.id.nav_host_fragment_activity_main, RegistrationFragment())
            ft.addToBackStack(null)
            ft.commit()
        }

        authLoginBtn.setOnClickListener(this)
        showPasswordImageView.setOnClickListener(this)
        returnButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            returnButton.id -> {
                requireActivity().supportFragmentManager.popBackStack()
            }
            showPasswordImageView.id -> {
                if (isPasswordVisible) {
                    showPasswordImageView.setImageResource(R.drawable.ic_password_hidden)
                    passwordEditText.transformationMethod = PasswordTransformationMethod()
                    isPasswordVisible = false
                } else {
                    showPasswordImageView.setImageResource(R.drawable.ic_password_visible)
                    passwordEditText.transformationMethod = null
                    isPasswordVisible = true
                }
                passwordEditText.setSelection(passwordEditText.length())
            }
            authLoginBtn.id -> {
                val apiInterface = RetrofitClient().getClient().create(AuthApi::class.java)

                val call1: Call<User> = apiInterface.auth(
                    loginEditText.text.toString(),
                    passwordEditText.text.toString()
                )
                call1.enqueue(object : Callback<User> {

                    override fun onResponse(
                        call: Call<User>,
                        response: Response<User>
                    ) {
                        if (response.isSuccessful) {

                            response.body()?.let {
                                it.id?.let { it1 ->
                                    sharedPreferencesRepository.setUserId(
                                        it1
                                    )
                                }
                                sharedPreferencesRepository.setUserToken(
                                    it.token
                                )
                                sharedPreferencesRepository.setUserName(
                                    it.name
                                )
                                it.image?.let { it1 ->
                                    sharedPreferencesRepository.setUserImage(
                                        it1
                                    )
                                }

                                it.id?.let { it1 -> sharedPreferencesRepository.setUserId(it1) }
                            }
                            if (CacheUtil().getBooleanValueByKey(
                                    requireContext(),
                                    Constant().RETURN_EVENT_DETAILS
                                )
                            ) {
                                CacheUtil().saveBooleanValue(
                                    requireContext(),
                                    Constant().RETURN_EVENT_DETAILS,
                                    false
                                )
                                requireActivity().supportFragmentManager.popBackStack()
                            } else
                                ActivityLauncher().startMainActivityWithFlags(requireContext())

                            return
                        }

                        Snackbar.make(view, response.message(), Snackbar.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Snackbar.make(view, t.message.toString(), Snackbar.LENGTH_LONG).show()
                        call.cancel()
                    }
                })
            }
        }
    }
}
