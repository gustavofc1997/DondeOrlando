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
import com.gforeroc.dondeorlando.data.IConfirmOrder
import com.gforeroc.dondeorlando.domain.NewOrder
import kotlinx.android.synthetic.main.dialog_check_order_list.*
import kotlinx.android.synthetic.main.dialog_quantity.*

class SummaryOrderDialogFragment(
    private val newOrder: NewOrder,
    private val iConfirmOrder: IConfirmOrder
) :
    DialogFragment() {

    private var window: Window? = null
    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        window = dialog?.window
        if (rootView == null) {
            rootView = inflater.inflate(
                R.layout.dialog_check_order_list, container, false
            )
        }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Rv_summary.setHasFixedSize(true)
        close_dialog_order.setOnClickListener { dismiss() }
        text_Total.text = newOrder.total.toString()
        val checkOrderAdapter = CheckOrderAdapter(newOrder.items)
        Rv_summary.apply {
            adapter = checkOrderAdapter
            layoutManager = LinearLayoutManager(context)
        }
        button_check.setOnClickListener {
            iConfirmOrder.confirmOrderListener()
            dialog?.dismiss()
            newOrder.clearData()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply { setLayout(530, 430) }
    }
}