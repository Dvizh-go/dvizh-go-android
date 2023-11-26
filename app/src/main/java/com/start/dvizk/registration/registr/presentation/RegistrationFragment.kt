package com.start.dvizk.registration.registr.presentation

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.start.dvizk.R
import com.start.dvizk.databinding.FragmentRegistrationBinding
import com.start.dvizk.registration.createpassword.presentation.PasswordGenerationFragment
import com.start.dvizk.registration.dialog.GenderSelectionDialog
import com.start.dvizk.registration.dialog.GenderSelectionListener
import com.start.dvizk.registration.registr.presentation.model.User
import com.start.dvizk.registration.varification.presentation.VerificationCodeFragment
import java.util.Calendar
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val PICK_IMAGE_REQUEST = 1
private const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123

class RegistrationFragment :
    Fragment(R.layout.fragment_registration),
    OnClickListener,
    GenderSelectionListener {

    private val registrationViewModel: RegistrationViewModel by viewModel<RegistrationViewModel>()
    private val binding: FragmentRegistrationBinding by viewBinding()

    private lateinit var fragment_registration_loader: View

    var filePath = ""
    var birthday = ""
    var gender = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        fragment_registration_loader = requireActivity().findViewById(R.id.progress_bar)
        fragmentRegistrationReturnButton.setNavigationOnClickListener { activity?.onBackPressed() }
        fragmentRegistrationUserImage.setOnClickListener(this@RegistrationFragment)
        fragmentRegistrationUserGenderSpinner.setOnClickListener(this@RegistrationFragment)
        fragmentRegistrationUserBirthdayTextView.setOnClickListener(this@RegistrationFragment)
        fragmentRegistrationContinue.setOnClickListener(this@RegistrationFragment)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImageUri = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

            val cursor = selectedImageUri?.let {
                activity?.contentResolver?.query(
                    it,
                    filePathColumn,
                    null,
                    null,
                    null
                )
            }
            cursor?.moveToFirst()

            val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
            filePath = columnIndex?.let { cursor.getString(it) }.toString()

            Glide.with(this)
                .asBitmap()
                .load(selectedImageUri)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.fragmentRegistrationUserImage)

            cursor?.close()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                openGallery()
            } else {
                // Разрешение не получено, выполните альтернативные действия
            }
        }
    }

    override fun onClick(view: View?): Unit = with(binding) {
        when (view?.id) {
            fragmentRegistrationUserGenderSpinner.id -> {
                val dialog = GenderSelectionDialog()
                dialog.setListener(this@RegistrationFragment)
                dialog.show(requireActivity().supportFragmentManager, "GenderSelectionDialog")
            }
            fragmentRegistrationUserBirthdayTextView.id -> {
                getBirthday()
            }
            fragmentRegistrationContinue.id -> {
                val user = User(
                    email = fragmentRegistrationUserEmailEditText.text.toString(),
                    password = null,
                    name = fragmentRegistrationUserNameEditText.text.toString(),
                    nickname = fragmentRegistrationUserNicknameEditText.text.toString(),
                    phone_number = fragmentRegistrationUserPhoneEditText.text.toString(),
                    gender = gender,
                    birthday = birthday,
                    image = filePath,
                    token = ""
                )

                val bundle = Bundle().apply {
                    putParcelable("user_regis", user)
                }
                val ft: FragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                val fragment = PasswordGenerationFragment()
                fragment.arguments = bundle
                ft.add(R.id.nav_host_fragment_activity_main, fragment)
                ft.addToBackStack(null)
                ft.commit()
            }
            fragmentRegistrationUserImage.id -> {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                    )
                } else {
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    intent.type = "image/*"
                    startActivityForResult(intent, PICK_IMAGE_REQUEST)
                }
            }
        }
    }

    override fun getGender(gender: String) {
        if (gender == "male") {
            binding.fragmentRegistrationUserGenderSpinner.text = "Мужчина"
        } else {
            binding.fragmentRegistrationUserGenderSpinner.text = "Женщина"
        }

        this.gender = gender
    }

    private fun initObservers() {
        registrationViewModel.registrationStateLiveData.observe(viewLifecycleOwner, ::handleState)
    }

    private fun handleState(state: RegistrationState) {
        when (state) {
            is RegistrationState.Failed -> {
                fragment_registration_loader.visibility = View.GONE
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
            }
            is RegistrationState.Loading -> {
                fragment_registration_loader.visibility = View.VISIBLE
            }
            is RegistrationState.Success -> {
                fragment_registration_loader.visibility = View.GONE
                val bundle = Bundle().apply {
                    putString("email", binding.fragmentRegistrationUserEmailEditText.text.toString())
                }
                val ft: FragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                val fragment = VerificationCodeFragment()
                fragment.arguments = bundle
                ft.add(R.id.fragment_container, fragment)
                ft.addToBackStack(null)
                ft.commit()
            }
        }
    }

    private fun getBirthday() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { view, year, month, dayOfMonth ->
                binding.fragmentRegistrationUserBirthdayTextView.text = "$year-${month + 1}-$dayOfMonth"
                birthday = "$year-${month + 1}-$dayOfMonth"
            }, year, month, dayOfMonth
        )

        datePickerDialog.show()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
}
