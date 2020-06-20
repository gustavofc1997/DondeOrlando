package com.gforeroc.dondeorlando.ui.stock

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.ui.base.OnQuantityUpdate
import com.gforeroc.dondeorlando.data.models.Product
import com.gforeroc.dondeorlando.domain.ProductOrder
import com.gforeroc.dondeorlando.ui.base.BaseFragment
import com.gforeroc.dondeorlando.ui.stock.adapter.StockAdapter
import com.gforeroc.dondeorlando.ui.stock.viewmodel.StockBeveragesViewModel
import com.gforeroc.dondeorlando.utils.IProductSelected
import com.gforeroc.dondeorlando.utils.OnProductOrderAdded
import com.gforeroc.dondeorlando.utils.QuantityUpdateDialog
import org.koin.android.viewmodel.ext.android.viewModel

class StockBeveragesFragment() : BaseFragment(),
    IProductSelected,
    OnProductOrderAdded, OnQuantityUpdate {

    var stockAdapter = StockAdapter(this)
    private val stockBeveragesViewModel : StockBeveragesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_beverages_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                layoutManager = GridLayoutManager(context, columnCount)
                adapter = stockAdapter
            }
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stockBeveragesViewModel.stockBeverages.observe(this, Observer {
            stockAdapter.setItems(it)
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
        val dialog =  QuantityUpdateDialog.newInstance(product, this)
        childFragmentManager.let { dialog.show(it, "null") }
    }

    override fun setProduct(product: ProductOrder) {
        onProductOrderAdded?.setProduct(product)
    }

    override fun updateQuantity(setUpdateQuantity: Long, id:String) {
        stockBeveragesViewModel.updateQuantity(setUpdateQuantity, id)
    }

}
