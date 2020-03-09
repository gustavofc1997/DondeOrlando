package com.gforeroc.dondeorlando.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.gforeroc.dondeorlando.R
import kotlinx.android.synthetic.main.dialog_quantity_update.*

class QuantityUpdateDialog(): DialogFragment() {
    private var window: Window? = null
    private var rootView: View? = null

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
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_update.setOnClickListener{

        }
    }
}