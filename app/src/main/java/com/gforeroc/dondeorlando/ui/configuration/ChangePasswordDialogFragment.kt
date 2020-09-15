package com.gforeroc.dondeorlando.ui.configuration

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.utils.DEFAULT_PASSWORD
import com.gforeroc.dondeorlando.utils.KEY_PASSWORD
import com.pixplicity.easyprefs.library.Prefs
import es.dmoral.toasty.Toasty
import ir.androidexception.andexalertdialog.AndExAlertDialog
import kotlinx.android.synthetic.main.fragment_change_password.*

class ChangePasswordDialogFragment : DialogFragment() {

    private var mContext: Context? = null
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
                R.layout.fragment_change_password, container, false
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
        mContext = this.activity?.applicationContext
        setHasOptionsMenu(true)
        close_dialog.setOnClickListener { dismiss() }
        close_dialog_confirm.setOnClickListener { dismiss() }

        btn_update_dialog.setOnClickListener {
            val length = et_update.text?.length ?: 0
            val myPrefs =
                Prefs.getString(KEY_PASSWORD, DEFAULT_PASSWORD)
            val passwordConfirm = et_update.text.toString()
            if (length > 3 && passwordConfirm.contains(myPrefs.toString())) {
                cl_next.visibility = View.GONE
                linear_confirm.visibility = View.VISIBLE
            } else context?.let { toasty ->
                Toasty.error(
                    toasty,
                    getString(R.string.pass_error),
                    Toast.LENGTH_LONG,
                    true
                ).show()
            }
        }

        btn_update_dialog_confirm.setOnClickListener {
            val lengthConfirm = et_update_pass.text?.length ?: 0
            if (lengthConfirm == 4) {
                val password = et_update_pass.text.toString()
                val passwordConfirm = et_update_confirm.text.toString()
                if (password == passwordConfirm) {
                    Prefs.putString(KEY_PASSWORD, password)
                    Prefs.edit().apply()
                    showWarningDialog()
                    dismiss()
                } else context?.let { toasty ->
                    Toasty.error(
                        toasty,
                        getString(R.string.pass_not_match),
                        Toast.LENGTH_LONG,
                        true
                    ).show()
                }
            } else {
                context?.let { toasty ->
                    Toasty.info(
                        toasty,
                        getString(R.string.msg_numbers),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun showWarningDialog() {
        AndExAlertDialog.Builder(context)
            .setTitle(getString(R.string.title_alert_dialog))
            .setMessage(getString(R.string.msg_alert_dialog))
            .setPositiveBtnText(getString(R.string.btn_close_alert_dialog))
            .setCancelableOnTouchOutside(false)
            .OnPositiveClicked {
            }
            .build();
    }
}

