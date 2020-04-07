package com.gforeroc.dondeorlando.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.data.IConfirmOrder
import com.gforeroc.dondeorlando.domain.NewOrder
import com.gforeroc.dondeorlando.domain.ProductOrder
import com.gforeroc.dondeorlando.ui.PageAdapter
import com.gforeroc.dondeorlando.utils.OnProductOrderAdded
import com.gforeroc.dondeorlando.utils.SummaryOrderDialogFragment
import com.gforeroc.dondeorlando.viewmodels.OrdersViewModel
import ir.androidexception.andexalertdialog.AndExAlertDialog
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(), OnProductOrderAdded, IConfirmOrder {

    private var mContext: Context? = null
    private lateinit var newOrder: NewOrder

    private val ordersViewModel: OrdersViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newOrder = NewOrder()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = this.activity?.applicationContext
        setHasOptionsMenu(true)
        val adapter = PageAdapter(
            childFragmentManager,
            this
        )
        viewpager.adapter = adapter
        tabCategories.setupWithViewPager(viewpager)
    }

    override fun setProduct(product: ProductOrder) {
        newOrder.addProduct(product)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun sendOrder() {
        if (canFinishOrder()) {
            newOrder.setDate()
            newOrder.calculateTotals()
            val dialog = SummaryOrderDialogFragment(newOrder, this)
            childFragmentManager.let { dialog.show(it, "SummaryOrderDialogFragment") }
        } else
            showWarningDialog()
    }

    private fun canFinishOrder(): Boolean {
        return newOrder.items.isNotEmpty()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cancel_order -> findNavController().popBackStack()
            R.id.finish_order -> sendOrder()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun confirmOrderListener() {
        ordersViewModel.sendOrder(newOrder)
    }

    private fun showWarningDialog() {
        AndExAlertDialog.Builder(context)
            .setTitle("Oopss")
            .setMessage("Debes agregar productos primero")
            .setPositiveBtnText("Cerrar")
            .setCancelableOnTouchOutside(false)
            .OnPositiveClicked {
            }
            .build();
    }

}



