package com.gforeroc.dondeorlando.ui.base

import androidx.fragment.app.Fragment
import com.gforeroc.dondeorlando.ui.ProductsAdapter
import com.gforeroc.dondeorlando.utils.IProductAdded
import com.gforeroc.dondeorlando.viewmodels.BaseViewModel
import com.gforeroc.dondeorlando.viewmodels.MeatViewModel

open class BaseFragment : Fragment() {
    internal var columnCount = 4
    internal var listener: IProductAdded? = null
    internal lateinit var viewModel: BaseViewModel
    internal open lateinit var productsAdapter: ProductsAdapter
}