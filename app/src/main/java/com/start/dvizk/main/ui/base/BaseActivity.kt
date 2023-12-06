package com.start.dvizk.main.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import by.kirich1409.viewbindingdelegate.viewBinding
import com.start.dvizk.R
import com.start.dvizk.databinding.ActivityBaseBinding
import com.start.dvizk.util.BaseFormManager
import com.start.dvizk.util.Constant

class BaseActivity : AppCompatActivity() {

    private val viewBinding: ActivityBaseBinding by viewBinding()
    private var formArgs: Bundle? = null
    private var formName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        formArgs = intent.getBundleExtra(Constant().FORM_DATA)
        formName = intent.getStringExtra(Constant().FORM_NAME)
        bindFragment(formName!!, formArgs)
        bindToolbar()
    }

    private fun bindToolbar() {
        viewBinding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun bindFragment(formName: String, formArgs: Bundle?) {
        val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        val homeFragment = BaseFormManager.getStartFormByName(formName, formArgs = formArgs)
        ft.replace(R.id.fragmentBasePlace, homeFragment)
        ft.commit()
    }
}
