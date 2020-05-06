package com.gforeroc.dondeorlando.utils

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.data.IDeleteOrders
import com.gforeroc.dondeorlando.databinding.DialogPasswordBinding
import com.pixplicity.easyprefs.library.Prefs
import ir.androidexception.andexalertdialog.AndExAlertDialog
import kotlinx.android.synthetic.main.dialog_password.*


class PasswordDialogFragment(private val iDeleteOrders : IDeleteOrders) : DialogFragment(), OnNumberClickListener {

    private var window: Window? = null
    private var adminPassword = "1111"

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
        btn_remove.setOnClickListener { removeAt() }
        et_code.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_code.text?.length!! > 3) {
                    val password = et_code.text.toString()
                    val myPrefs = Prefs.getInt(adminPassword, password.toInt())
                    if (password.contains(myPrefs.toString())) {
                        iDeleteOrders.deletOrdersListener()
                        et_code.setText("")
                    } else {
                        et_code.setText("")
                        showWarningDialog()
                    }
                }
            }
        })
    }

    private fun removeAt() {
        val removeAt = et_code.text?.length
        if (removeAt != null) {
            if (removeAt > 0) {
                et_code.text?.delete(removeAt - 1, removeAt)
            }
        }
    }

    private fun showWarningDialog() {
        AndExAlertDialog.Builder(context)
            .setTitle("Oopss")
            .setMessage("Codigo Erroneo")
            .setPositiveBtnText("Cerrar")
            .setCancelableOnTouchOutside(false)
            .OnPositiveClicked {
            }
            .build();
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