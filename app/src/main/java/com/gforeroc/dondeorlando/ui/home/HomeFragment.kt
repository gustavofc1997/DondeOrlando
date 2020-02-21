package com.gforeroc.dondeorlando.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.data.Product
import com.gforeroc.dondeorlando.domain.ProductOrder
import com.gforeroc.dondeorlando.ui.PageAdapter
import com.gforeroc.dondeorlando.utils.IProductSelected
import com.gforeroc.dondeorlando.utils.OnProductOrderAdded
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() , OnProductOrderAdded {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter =
            PageAdapter(childFragmentManager,this)
        viewpager.adapter = adapter
        tabCategories.setupWithViewPager(viewpager)
    }

    override fun setProduct(product: ProductOrder) {
        
    }

}