package com.gforeroc.dondeorlando.ui.base

import androidx.fragment.app.Fragment
import com.gforeroc.dondeorlando.ui.home.adapter.ProductsAdapter
import com.gforeroc.dondeorlando.utils.OnProductOrderAdded

open class BaseFragment : Fragment() {
    internal var columnCount = 4
    internal open var  onProductOrderAdded: OnProductOrderAdded?=null
    internal open lateinit var productsAdapter: ProductsAdapter
}