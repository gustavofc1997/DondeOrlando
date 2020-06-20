package com.gforeroc.dondeorlando.utils

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.ui.base.IPasswordAction
import com.gforeroc.dondeorlando.databinding.KeypadBinding
import com.pixplicity.easyprefs.library.Prefs
import ir.androidexception.andexalertdialog.AndExAlertDialog
import kotlinx.android.synthetic.main.keypad.*


class PasswordDialogFragment : DialogFragment(), OnNumberClickListener {

    private var window: Window? = null
    private lateinit var delegate: IPasswordAction
    private var isDismissible: Boolean = true

    companion object {
        fun newInstance(
            iPasswordAction: IPasswordAction,
            isDismissible: Boolean
        ): PasswordDialogFragment {
            return PasswordDialogFragment().apply {
                setDelegate(iPasswordAction)
                setDismissible(isDismissible)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: KeypadBinding =
            DataBindingUtil.inflate(inflater, R.layout.keypad, container, false)
        binding.listener = this
        window = dialog?.window
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setCancelable(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isDismissible) {
            close_dialog_password.visibility = View.VISIBLE
        }
        close_dialog_password.setOnClickListener { dismiss() }
        btn_remove.setOnClickListener { removeAt() }
        et_code.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val length = et_code.text?.length ?: 0
                if (length > 3) {
                    val password = et_code.text.toString()
                    val myPrefs =
                        Prefs.getString(KEY_PASSWORD, DEFAULT_PASSWORD)
                    if (password.contains(myPrefs.toString())) {
                        delegate.onPasswordSuccessful()
                        et_code.setText("")
                        dismiss()
                    } else {
                        et_code.setText("")
                        showWarningDialog()
                    }
                }
            }
        })
    }

    private fun setDismissible(dismiss: Boolean) {
        isDismissible = dismiss
    }

    private fun setDelegate(iPasswordAction: IPasswordAction) {
        delegate = iPasswordAction
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
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

    override fun onNumberClick(view: View, number: Int) {
        val values = et_code.text.toString().plus(number.toString())
        et_code.setText(values)
    }
}

interface OnNumberClickListener {
    fun onNumberClick(view: View, number: Int)
}