package com.gforeroc.dondeorlando.ui.stock

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.ui.stock.adapter.StockPageAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class StockFragment : Fragment() {

    private var mContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = this.activity?.applicationContext
        setHasOptionsMenu(true)
        val adapter = StockPageAdapter(
            childFragmentManager
        )
        viewpager.adapter = adapter
        tabCategories.setupWithViewPager(viewpager)
    }
}

