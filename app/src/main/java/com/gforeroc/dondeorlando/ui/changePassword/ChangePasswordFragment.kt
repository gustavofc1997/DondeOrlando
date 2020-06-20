package com.gforeroc.dondeorlando.ui.changePassword

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.ui.base.IPasswordAction
import com.gforeroc.dondeorlando.ui.base.IShowOrders
import com.gforeroc.dondeorlando.utils.KEY_PASSWORD
import com.gforeroc.dondeorlando.utils.PasswordDialogFragment
import com.pixplicity.easyprefs.library.Prefs
import ir.androidexception.andexalertdialog.AndExAlertDialog
import kotlinx.android.synthetic.main.fragment_update_password.*

class ChangePasswordFragment : Fragment() {

    private var mContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showPasswordDialog(object :
            IShowOrders {
            override fun onPasswordSuccessful() {
                cl_orders.visibility = View.VISIBLE
            }
        })

        return inflater.inflate(R.layout.fragment_update_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext = this.activity?.applicationContext
        setHasOptionsMenu(true)

        btn_update_pass.setOnClickListener {
            val lenght = et_update.text?.length ?: 0
            if (lenght == 4) {
                val password = et_update.text.toString()
                Prefs.putString(KEY_PASSWORD, password)
                Prefs.edit().apply()
                showWarningDialog()
                et_update.setText("")
            } else {
                Toast.makeText(context, "Solo 4 Numeros", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showPasswordDialog(listener: IPasswordAction) {
        val dialog = PasswordDialogFragment.newInstance(listener, false)
        childFragmentManager.let { dialog.show(it, "PasswordDialog") }
    }

    private fun showWarningDialog() {
        AndExAlertDialog.Builder(context)
            .setTitle("Genial!")
            .setMessage("Has actualizado tu clave, no la olvides")
            .setPositiveBtnText("Cerrar")
            .setCancelableOnTouchOutside(false)
            .OnPositiveClicked {
            }
            .build();
    }
}