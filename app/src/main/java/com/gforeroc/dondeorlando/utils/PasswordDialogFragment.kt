package com.gforeroc.dondeorlando.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.databinding.DialogPasswordBinding
import kotlinx.android.synthetic.main.dialog_password.*

class PasswordDialogFragment : DialogFragment(), OnNumberClickListener {

    private var window: Window? = null
    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: DialogPasswordBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_password, container, false)
        binding.listener = this
        window = dialog?.window
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        close_dialog_password.setOnClickListener { dismiss() }
        btn_remove.setOnClickListener {
            removeAt()
        }
    }

    private fun removeAt() {
        val removeAt = et_code.text?.length
        if (removeAt != null) {
            if (removeAt > 0){
                et_code.text?.delete(removeAt -1, removeAt)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.apply { setLayout(480, 630) }
    }

    override fun onNumberClick(view: View, number: Int) {
        val values = et_code.text.toString().plus(number.toString())
        et_code.setText(values)
    }
}

interface OnNumberClickListener {
    fun onNumberClick(view: View, number: Int)
}