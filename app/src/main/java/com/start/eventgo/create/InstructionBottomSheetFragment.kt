package com.start.eventgo.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.start.eventgo.R

class InstructionBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_instruction, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
    }

    private fun initView(view: View) {
        val buttonContinue = view.findViewById<Button>(R.id.btnContinue)

        buttonContinue.setOnClickListener {
            dismiss()
        }
    }

    override fun getTheme() = R.style.CustomBottomSheetDialogTheme

    companion object {
        fun getInstance(args: Bundle?): InstructionBottomSheetFragment {
            val fragment = InstructionBottomSheetFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
