package com.gforeroc.dondeorlando.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.data.models.Product
import com.gforeroc.dondeorlando.ui.base.OnQuantityUpdate
import kotlinx.android.synthetic.main.dialog_quantity_update.*
import kotlinx.android.synthetic.main.keypad.*

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        window = dialog?.window
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.dialog_quantity_update, container, false)
        }
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
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
            }else{
                val updateEdit = edit_quantity.text.toString().toLong()
                val newQuantity = products.Amount.plus(updateEdit)
                onQuantityUpdate.updateQuantity(newQuantity, id)
            }
            dismissAllowingStateLoss()
        }
    }
}