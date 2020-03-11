package com.gforeroc.dondeorlando.ui.stock.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gforeroc.dondeorlando.ui.stock.StockAdditionalFragment
import com.gforeroc.dondeorlando.ui.stock.StockBeveragesFragment
import com.gforeroc.dondeorlando.ui.stock.StockMeatFragment
import com.gforeroc.dondeorlando.ui.stock.StockOthersFragment

class StockPageAdapter(manager: FragmentManager) :
    FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> StockMeatFragment()
            1 -> StockBeveragesFragment()
            2 -> StockAdditionalFragment()
            3 -> StockOthersFragment()
            else -> StockMeatFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Carnes"
            1 -> "Bebidas"
            2 -> "Acompañamientos"
            3 -> "Otros"
            else -> null
        }
    }
}