package com.gforeroc.dondeorlando.ui.meat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.ui.ProductsAdapter
import com.gforeroc.dondeorlando.ui.base.BaseFragment
import com.gforeroc.dondeorlando.utils.IProductAdded
import com.gforeroc.dondeorlando.viewmodels.MeatViewModel

class MeatFragment : BaseFragment() {

    override var productsAdapter = ProductsAdapter(listener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meat_list, container, false)
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager =
                    GridLayoutManager(context, columnCount)
                adapter = productsAdapter
            }
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MeatViewModel::class.java)
        viewModel.taskList.observe(this, Observer {
            productsAdapter.setItems(it)
        })
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IProductAdded) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}
