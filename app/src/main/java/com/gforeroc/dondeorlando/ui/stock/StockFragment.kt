package com.gforeroc.dondeorlando.ui.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.data.Product
import com.gforeroc.dondeorlando.ui.base.BaseFragment
import com.gforeroc.dondeorlando.ui.stock.adapter.StockAdapter
import com.gforeroc.dondeorlando.utils.IProductSelected
import com.gforeroc.dondeorlando.utils.QuantityUpdateDialog

class StockFragment : BaseFragment(),IProductSelected {

    override var stockAdapter = StockAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recycler_stock, container, false)
        if (view is RecyclerView){
            with(view){
                adapter = stockAdapter
                layoutManager
            }
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onProductSelected(product: Product) {
        QuantityUpdateDialog().show(childFragmentManager, "")
    }

}
