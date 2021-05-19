package com.gforeroc.dondeorlando.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.data.models.Product
import com.gforeroc.dondeorlando.domain.ProductOrder
import kotlinx.android.synthetic.main.dialog_quantity.*

class QuantityDialog(var product: Product, private val quantityAdded: OnProductOrderAdded) :
    DialogFragment() {
    private var window: Window? = null
    private var rootView: View? = null

    companion object {
        fun newInstance(
            product: Product,
            quantityAdded: OnProductOrderAdded
        ): QuantityDialog {
            return QuantityDialog(product, quantityAdded)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        window = dialog?.window
        if (rootView == null) {
            rootView = inflater.inflate(
                R.layout.dialog_quantity, container, false
            )
        }
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        close_dialog_quantity.setOnClickListener { dismiss() }
        qty_orden.setBackgroundColor(resources.getColor(R.color.colorAccent))
        qty_orden.max = product.Amount.toInt()
        tv_product_name.text = product.Name
        chk_additional.setOnClickListener {
            if (chk_additional.isChecked) {
                et_additional_price.visibility = View.VISIBLE
            } else {
                et_additional_price.visibility = View.GONE
            }
        }

        btn_add.setOnClickListener {
            val productOrder = ProductOrder(product.copy(), qty_orden.value)
            productOrder.isAdditional = chk_additional.isChecked
            if (chk_additional.isChecked) {
                if (et_price.text?.isNotEmpty()!!){
                    productOrder.product.Additional = et_price.text.toString()
                }
            }
            quantityAdded.setProduct(productOrder)
            dismiss()
        }
    }
}

interface OnProductOrderAdded {
    fun setProduct(product: ProductOrder)
}