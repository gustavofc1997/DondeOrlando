package com.gforeroc.dondeorlando.utils

import android.graphics.Point
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.data.models.Product
import com.gforeroc.dondeorlando.ui.base.OnQuantityUpdate
import kotlinx.android.synthetic.main.dialog_quantity_update.*

class QuantityUpdateDialog(
    private var onQuantityUpdate: OnQuantityUpdate,
    private var products: Product
) : DialogFragment() {
    private var window: Window? = null
    private var rootView: View? = null

    companion object {
        fun newInstance(
            products: Product,
            onQuantityUpdate: OnQuantityUpdate
        ): QuantityUpdateDialog {
            return QuantityUpdateDialog(onQuantityUpdate, products)
        }
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
        window?.setLayout((width * 0.4).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        window = dialog?.window
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.dialog_quantity_update, container, false)
        }
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        close_dialog_update.setOnClickListener { dismiss() }
        btn_update.setOnClickListener {
            val id = products.id
            if (chk_delete.isChecked) {
                onQuantityUpdate.updateQuantityCheck(id)
                dismissAllowingStateLoss()
            } else {
                if (edit_quantity.text?.isEmpty()!!) {
                    edit_quantity.error = "Debes ingresar una cantidad"
                    return@setOnClickListener
                }
                val updateEdit = edit_quantity.text.toString().toLong()
                val newQuantity = products.Amount.plus(updateEdit)
                onQuantityUpdate.updateQuantity(newQuantity, id)
                dismissAllowingStateLoss()

            }
        }
    }
}