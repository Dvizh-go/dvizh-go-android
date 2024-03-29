package com.start.eventgo.create.steps.discount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.start.eventgo.R

class DiscountStepFragment : Fragment() {

    private lateinit var next: Button
    private lateinit var back: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_discount_step, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
    }

    private fun initView(view: View) {
        next = view.findViewById(R.id.fragment_create_organization_next)
        back = view.findViewById(R.id.fragment_create_organization_back)

        next.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
