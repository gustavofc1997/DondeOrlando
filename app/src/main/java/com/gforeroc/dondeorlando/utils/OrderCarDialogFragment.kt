package com.gforeroc.dondeorlando.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gforeroc.dondeorlando.CheckOrderAdapter
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.domain.NewOrder
import kotlinx.android.synthetic.main.dialog_car_orde_list.*

class OrderCarDialogFragment(private val newOrder: NewOrder) : DialogFragment() {

    private var windows: Window? = null
    private var rootview: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        windows = dialog?.window
        if (rootview == null) {
            rootview = inflater.inflate(R.layout.dialog_car_orde_list, container, false)
        }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)
        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Rv_summary_car.setHasFixedSize(true)
        text_total_car.text = newOrder.total.toString()
        val checkOrderAdapter = CheckOrderAdapter(newOrder.items)
        Rv_summary_car.apply {
            adapter = checkOrderAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply { setLayout(530, 430) }
    }
}