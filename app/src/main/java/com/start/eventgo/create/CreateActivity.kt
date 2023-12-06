package com.start.eventgo.create

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.start.eventgo.R
import com.start.eventgo.arch.data.SharedPreferencesRepository
import com.start.eventgo.create.organization.list.presentation.OrganizationListFragment
import org.koin.android.ext.android.inject

class CreateActivity : AppCompatActivity() {

    private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutInflater.inflate(R.layout.activity_auth, null, false))

        if (sharedPreferencesRepository.getFirstLaunchInstructio()) {
            sharedPreferencesRepository.setFirstLaunchInstructio(false)

            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()

            ft.replace(R.id.fragment_container, InstructionFragment())

            ft.commit()
        } else {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()

            ft.replace(R.id.fragment_container, OrganizationListFragment())

            ft.commit()
        }
    }
}
