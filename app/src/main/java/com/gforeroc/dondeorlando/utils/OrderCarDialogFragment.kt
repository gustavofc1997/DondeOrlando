package com.gforeroc.dondeorlando.utils

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gforeroc.dondeorlando.CheckOrderAdapter
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.data.IConfirmOrder
import com.gforeroc.dondeorlando.domain.NewOrder
import kotlinx.android.synthetic.main.dialog_car_order.*


class OrderCarDialogFragment(
    private val newOrder: NewOrder,
    private val iConfirmOrder: IConfirmOrder
) : DialogFragment() {

    private var rootview: View? = null

    companion object {
        fun newInstance(
            newOrder: NewOrder
            , iConfirmOrder: IConfirmOrder
        ): OrderCarDialogFragment {
            return OrderCarDialogFragment(newOrder, iConfirmOrder)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootview == null) {
            rootview = inflater.inflate(R.layout.dialog_car_order, container, false)
        }
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        return rootview
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val window = dialog?.window;
        window?.let {
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(it.attributes)
            val displayMetrics = DisplayMetrics()
            activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
            val widthLcl = (displayMetrics.widthPixels * 0.9f).toInt()
            val heightLcl = (displayMetrics.heightPixels * 0.8f).toInt()
            lp.width = widthLcl
            lp.height = heightLcl
            it.attributes = lp
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        close_dialog_car.setOnClickListener { dismiss() }
        text_total_car.text = newOrder.total.toString()
        val checkOrderAdapter = CheckOrderAdapter(newOrder.items)
        Rv_summary_car.apply {
            adapter = checkOrderAdapter
            layoutManager = LinearLayoutManager(context)
        }
        btn.setOnClickListener {
            iConfirmOrder.confirmOrderListener()
            dialog?.dismiss()
            newOrder.clearData()
        }
    }
}