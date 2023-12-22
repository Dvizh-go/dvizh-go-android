package com.start.eventgo.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.start.eventgo.R
import com.start.eventgo.arch.data.SharedPreferencesRepository
import com.start.eventgo.auth.main.MainAuthFragment
import com.start.eventgo.main.ui.home.presentation.HomeFragment
import com.start.eventgo.main.ui.profile.presentation.ProfileFragment
import com.start.eventgo.main.ui.tickets.mytickets.presentation.MyTicketsFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val prefsRepository: SharedPreferencesRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null, false)
        setContentView(view)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.nav_bg)

        println("---------------------" + SharedPreferencesRepository(this).getUserToken())
        println("---------------------" + SharedPreferencesRepository(this).getUserId())

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
                    val homeFragment = HomeFragment()
                    ft.replace(R.id.nav_host_fragment_activity_main, homeFragment)
                    ft.commit()

                    true
                }
// 				R.id.navigation_favorites -> {
// 					val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
// 					val favoritesFragment = FavoritesFragment()
// 					ft.replace(R.id.nav_host_fragment_activity_main, favoritesFragment)
// 					ft.commit()
//
// 					true
// 				}
//                R.id.navigation_qr -> {
//                    ActivityLauncher().startQrScanner(this)
//
//                    true
//                }
                R.id.navigation_my_tickets -> {
                    val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
                    val myTicketsFragment = MyTicketsFragment()
                    ft.replace(R.id.nav_host_fragment_activity_main, myTicketsFragment)
                    ft.commit()

                    true
                }
                R.id.navigation_profile -> {

                    if (prefsRepository.getUserToken() == prefsRepository.no_value) {
                        val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
                        ft.replace(R.id.nav_host_fragment_activity_main, MainAuthFragment())
                        ft.commit()

                        return@setOnItemSelectedListener true
                    }

                    val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
                    val profileFragment = ProfileFragment()
                    ft.replace(R.id.nav_host_fragment_activity_main, profileFragment)
                    ft.commit()

                    true
                }
                else -> {
                    true
                }
            }
        }
    }
}