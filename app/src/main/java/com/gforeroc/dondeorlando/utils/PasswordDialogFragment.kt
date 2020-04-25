package com.gforeroc.dondeorlando.utils

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.gforeroc.dondeorlando.BR
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.databinding.DialogPasswordBinding
import kotlinx.android.synthetic.main.dialog_password.*
import kotlinx.android.synthetic.main.dialog_password.view.*

class PasswordDialogFragment : DialogFragment() {

    private var window: Window? = null
    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: DialogPasswordBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_password, container, false)
        binding.listener = ClickHandler()
        binding.context
        window = dialog?.window
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }
    data class Number(var number: String)

    class ClickHandler {
        fun simpleButtonClick(view: View, number: Number) {
            val values = view.et_code.text.toString()
            values.plus(number)
            view.et_code.setText(values)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        close_dialog_password.setOnClickListener { dismiss() }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.apply { setLayout(480, 630) }
    }
}