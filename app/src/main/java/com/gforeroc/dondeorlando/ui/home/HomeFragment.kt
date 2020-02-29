package com.gforeroc.dondeorlando.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.Transition
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import androidx.navigation.navigation
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.domain.ProductOrder
import com.gforeroc.dondeorlando.ui.PageAdapter
import com.gforeroc.dondeorlando.utils.OnProductOrderAdded
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() , OnProductOrderAdded {

    private var mContext: Context? = null

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = this.activity
        setHasOptionsMenu(true)
        val adapter = PageAdapter(childFragmentManager,this)
        viewpager.adapter = adapter
        tabCategories.setupWithViewPager(viewpager)
    }

    override fun setProduct(product: ProductOrder) {
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.cancel_order -> findNavController().popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}