package com.gforeroc.dondeorlando.ui.base

import androidx.fragment.app.Fragment
import com.gforeroc.dondeorlando.ui.ProductsAdapter
import com.gforeroc.dondeorlando.utils.IProductSelected
import com.gforeroc.dondeorlando.utils.OnProductOrderAdded
import com.gforeroc.dondeorlando.viewmodels.BaseViewModel

open class BaseFragment : Fragment() {
    internal var columnCount = 4
    internal open var  onProductOrderAdded: OnProductOrderAdded?=null
    internal lateinit var viewModel: BaseViewModel
    internal open lateinit var productsAdapter: ProductsAdapter
}