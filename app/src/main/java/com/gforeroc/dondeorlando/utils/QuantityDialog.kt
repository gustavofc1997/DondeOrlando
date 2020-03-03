package com.gforeroc.dondeorlando.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.data.Product
import com.gforeroc.dondeorlando.domain.ProductOrder
import kotlinx.android.synthetic.main.dialog_quantity.*

class QuantityDialog(private val quantityAdded: OnProductOrderAdded, var product: Product) :
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
                R
                    .layout.dialog_quantity, container, false
            )
        }
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog)
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_add.setOnClickListener {
            val productOrder = ProductOrder(product, qty_orden.value)
            quantityAdded.setProduct(productOrder)
        }
    }
}

interface OnProductOrderAdded{
    fun setProduct(product: ProductOrder)
}