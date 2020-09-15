package com.gforeroc.dondeorlando.utils

import android.graphics.Point
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gforeroc.dondeorlando.CheckOrderAdapter
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.domain.NewOrder
import com.gforeroc.dondeorlando.ui.base.IConfirmOrder
import kotlinx.android.synthetic.main.dialog_car_order.*

class OrderCarDialogFragment(
    private val newOrder: NewOrder,
    private val iConfirmOrder: IConfirmOrder
) : DialogFragment() {

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
        val rv = inflater.inflate(R.layout.dialog_car_order, container, false)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
        return rv
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        val window = dialog!!.window
        val size = Point()
        val display = window?.windowManager?.defaultDisplay
        display?.getSize(size)
        val width: Int = size.x
        window?.setLayout((width * 0.85).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        close_dialog_car.setOnClickListener { dismiss() }
        text_total_car.text = newOrder.total.toInt().convertToMoney()
        val checkOrderAdapter = CheckOrderAdapter(newOrder.items)
        Rv_summary_car.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        Rv_summary_car.apply {
            adapter = checkOrderAdapter
            layoutManager = LinearLayoutManager(context)
        }
        btn.setOnClickListener {
            newOrder.Courtesy = chk_courtesy.isChecked
            iConfirmOrder.confirmOrderListener()
            dialog?.dismiss()
            newOrder.clearData()
        }
    }
}