package com.gforeroc.dondeorlando.ui.update

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.utils.DEFAULT_PASSWORD
import com.gforeroc.dondeorlando.utils.KEY_PASSWORD
import com.pixplicity.easyprefs.library.Prefs
import ir.androidexception.andexalertdialog.AndExAlertDialog
import kotlinx.android.synthetic.main.fragment_update_password.*

class UpdatePasswordFragment : Fragment() {

    private var mContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                showWarningDialog(password)
                et_update.setText("")
            } else {
                Toast.makeText(context, "Solo 4 Numeros", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showWarningDialog(message: String) {
        AndExAlertDialog.Builder(context)
            .setTitle("Success")
            .setMessage(message)
            .setPositiveBtnText("Cerrar")
            .setCancelableOnTouchOutside(false)
            .OnPositiveClicked {
            }
            .build();
    }
}