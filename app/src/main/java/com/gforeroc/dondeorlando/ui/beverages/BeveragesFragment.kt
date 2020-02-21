package com.gforeroc.dondeorlando.ui.beverages

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
import com.gforeroc.dondeorlando.data.Product
import com.gforeroc.dondeorlando.domain.ProductOrder
import com.gforeroc.dondeorlando.ui.ProductsAdapter
import com.gforeroc.dondeorlando.ui.base.BaseFragment
import com.gforeroc.dondeorlando.utils.IProductSelected
import com.gforeroc.dondeorlando.utils.OnProductOrderAdded
import com.gforeroc.dondeorlando.utils.QuantityDialog
import com.gforeroc.dondeorlando.viewmodels.BeveragesViewModel

class BeveragesFragment(override var onProductOrderAdded: OnProductOrderAdded?) : BaseFragment(),IProductSelected,
    OnProductOrderAdded {

    override var productsAdapter = ProductsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_beverages_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = GridLayoutManager(context, columnCount)
                adapter = productsAdapter
            }
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BeveragesViewModel::class.java)
        (viewModel as BeveragesViewModel).beverages.observe(this, Observer {
            productsAdapter.setItems(it)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnProductOrderAdded) {
            onProductOrderAdded = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        onProductOrderAdded = null
    }

    override fun onProductSelected(product: Product) {
        QuantityDialog(this,product).show(childFragmentManager,"null")
    }

    override fun setProduct(product: ProductOrder) {
        onProductOrderAdded?.setProduct(product)
    }

}
