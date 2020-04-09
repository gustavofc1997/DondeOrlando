package com.gforeroc.dondeorlando.ui.stock

import android.content.Context
import android.content.LocusId
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.data.OnQuantityUpdate
import com.gforeroc.dondeorlando.data.Product
import com.gforeroc.dondeorlando.data.SideRepository
import com.gforeroc.dondeorlando.domain.ProductOrder
import com.gforeroc.dondeorlando.ui.home.adapter.ProductsAdapter
import com.gforeroc.dondeorlando.ui.base.BaseFragment
import com.gforeroc.dondeorlando.ui.stock.viewmodel.VMStockAdditional
import com.gforeroc.dondeorlando.utils.IProductSelected
import com.gforeroc.dondeorlando.utils.OnProductOrderAdded
import com.gforeroc.dondeorlando.utils.QuantityUpdateDialog
import com.gforeroc.dondeorlando.viewmodels.SidesViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.android.viewModel

class StockAdditionalFragment() : BaseFragment(),IProductSelected,
    OnProductOrderAdded, OnQuantityUpdate {

    override var productsAdapter = ProductsAdapter(this)
    private val vmStockAdditional : VMStockAdditional by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_additional_list, container, false)
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
        vmStockAdditional.stockAdditional.observe(this, Observer {
            productsAdapter.setItems(it)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnProductOrderAdded) {
            onProductOrderAdded = context
        } else {
            return
        }
    }

    override fun onDetach() {
        super.onDetach()
        onProductOrderAdded = null
    }

    override fun onProductSelected(product: Product) {
        QuantityUpdateDialog(this, product).show(childFragmentManager, "null")
    }

    override fun setProduct(product: ProductOrder) {
        onProductOrderAdded?.setProduct(product)
    }

    override fun updateQuantity(setUpdateQuantity: Long, id: String) {
        vmStockAdditional.updateQuantity(setUpdateQuantity, id)
    }
}
