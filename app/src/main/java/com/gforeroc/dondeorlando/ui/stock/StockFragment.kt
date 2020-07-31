package com.gforeroc.dondeorlando.ui.stock

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.ui.stock.adapter.StockPageAdapter
import com.gforeroc.dondeorlando.utils.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.fragment_stock.*


class StockFragment : Fragment() {

    private var mContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = this.activity?.applicationContext
        setHasOptionsMenu(true)
        val adapter = StockPageAdapter(
            childFragmentManager
        )
        viewpager.setPageTransformer(true, ZoomOutPageTransformer())
        viewpager.adapter = adapter
        tabCategories.setupWithViewPager(viewpager)
    }
}

