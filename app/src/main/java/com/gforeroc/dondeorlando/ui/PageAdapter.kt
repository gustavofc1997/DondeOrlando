package com.gforeroc.dondeorlando.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gforeroc.dondeorlando.ui.additional.AdditionalFragment
import com.gforeroc.dondeorlando.ui.beverages.BeveragesFragment
import com.gforeroc.dondeorlando.ui.meat.MeatFragment
import com.gforeroc.dondeorlando.utils.IProductSelected
import com.gforeroc.dondeorlando.utils.OnProductOrderAdded

class PageAdapter(manager: FragmentManager, private var listener: OnProductOrderAdded?) :
    FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MeatFragment(listener)
            1 -> BeveragesFragment(listener)
            2 -> AdditionalFragment(listener)
            else -> MeatFragment(listener)
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