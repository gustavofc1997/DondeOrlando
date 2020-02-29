package com.gforeroc.dondeorlando.ui.initialize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gforeroc.dondeorlando.R
import kotlinx.android.synthetic.main.fragment_attract.*

class AttractFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_attract, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment_start.setOnClickListener{
            it.findNavController().navigate(R.id.action_nav_initialize_to_nav_home)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}