package com.start.eventgo.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.start.eventgo.R
import com.start.eventgo.auth.main.MainAuthFragment

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.nav_bg)

        setContentView(layoutInflater.inflate(R.layout.activity_auth, null, false))

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()

        ft.replace(R.id.fragment_container, MainAuthFragment())

        ft.commit()
    }
}
