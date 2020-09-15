package com.gforeroc.dondeorlando.ui.configuration

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gforeroc.dondeorlando.R
import kotlinx.android.synthetic.main.fragment_configuration.*

class ConfigurationFragment : Fragment() {

    private var mContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_configuration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = this.activity?.applicationContext
        setHasOptionsMenu(true)
        btn_update_pass.setOnClickListener {
            showPasswordDialogChange()
        }
    }

    private fun showPasswordDialogChange() {
        val dialog = ChangePasswordDialogFragment()
        childFragmentManager.let { dialog.show(it, "ChangePasswordDialog") }
        dialog.isCancelable = false
    }
}