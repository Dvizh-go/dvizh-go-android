package com.start.eventgo.create

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import by.kirich1409.viewbindingdelegate.viewBinding
import com.start.eventgo.R
import com.start.eventgo.create.dialog.OnSuccessDialogOk
import com.start.eventgo.create.dialog.SUCCESS_DIALOG_SUBTITLE
import com.start.eventgo.create.dialog.SUCCESS_DIALOG_TITLE
import com.start.eventgo.create.dialog.SuccessDialog
import com.start.eventgo.databinding.ActivityAuthBinding
import com.start.eventgo.util.BaseFormManager
import com.start.eventgo.util.Constant

class CreateActivity : AppCompatActivity(), OnSuccessDialogOk {

    private val viewBinding: ActivityAuthBinding by viewBinding()
    private var formArgs: Bundle? = null
    private var formName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        formArgs = intent.getBundleExtra(Constant().FORM_DATA)
        formName = intent.getStringExtra(Constant().FORM_NAME)
        bindFragment(formName!!, formArgs)
        bindToolbar()
    }

    private fun checkSuccessDialog(fragment: Fragment): Boolean {
        return fragment == SuccessDialog()
    }

    private fun bindToolbar() {
        viewBinding.fragmentRegistrationReturnButton.setNavigationOnClickListener {
            finish()
        }
    }

    private fun bindFragment(formName: String, formArgs: Bundle?) {
        val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
        val homeFragment = BaseFormManager.getStartFormByName(formName, formArgs = formArgs)
        if (checkSuccessDialog(homeFragment)) {
            val dialog = SuccessDialog()
            dialog.setListener(this)
            dialog.arguments = Bundle().apply {
                putString(SUCCESS_DIALOG_TITLE, "Поздравляем!")
                putString(SUCCESS_DIALOG_SUBTITLE, "Ваша заявка на рассмотрении у модераторов.")
            }
            dialog.show(supportFragmentManager, "successDialog")
        } else {
            ft.replace(R.id.fragment_container, homeFragment)
            ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
            ft.commit()
        }
    }

    override fun onPositiveClickButton() {
        supportFragmentManager.popBackStack()
    }
}
