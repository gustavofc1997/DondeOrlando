package com.gforeroc.dondeorlando.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gforeroc.dondeorlando.MainActivity
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.data.models.Product
import com.gforeroc.dondeorlando.domain.myOrders.MyOrder
import com.gforeroc.dondeorlando.ui.base.IDeleteOrders
import com.gforeroc.dondeorlando.ui.base.IPasswordAction
import com.gforeroc.dondeorlando.ui.base.IShowOrders
import com.gforeroc.dondeorlando.ui.orders.adapter.OrdersAdapter
import com.gforeroc.dondeorlando.utils.ADDITIONAL
import com.gforeroc.dondeorlando.utils.OrdersAction
import com.gforeroc.dondeorlando.utils.PasswordDialogFragment
import com.gforeroc.dondeorlando.utils.convertToMoney
import com.gforeroc.dondeorlando.viewmodels.OrdersViewModel
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_orders.*
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.forEach
import kotlin.collections.isNotEmpty
import kotlin.collections.map
import kotlin.collections.set
import kotlin.collections.sum


class OrdersFragment : Fragment() {

    private val ordersAdapter = OrdersAdapter()
    private val ordersViewModel: OrdersViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    private fun validateUser() {
        showPasswordDialog(object :
            IShowOrders {
            override fun onPasswordSuccessful() {
                cl_parent.visibility = View.VISIBLE
            }
        }, false)
    }

    private fun initObservers() {
        ordersViewModel.getError().observe(this) { result ->
            context?.let {
                when (result) {
                    OrdersAction.DELETE_SUCCESS -> {
                        Toasty.success(
                            it,
                            getString(R.string.msg_close_sell),
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    }

                    OrdersAction.DELETE_ERROR -> {
                        Toasty.success(
                            it,
                            getString(R.string.msg_error_close_sells),
                            Toast.LENGTH_SHORT,
                            true
                        ).show()

                    }
                    else -> {
                        Toasty.info(it, getString(R.string.msg_try_again), Toast.LENGTH_SHORT, true)
                            .show()
                    }
                }
            }
        }

        ordersViewModel.getLoading().observe(this) { isLoading ->
            if (isLoading != null) {
                if (isLoading) {
                    loadingPanel.visibility = View.VISIBLE
                } else {
                    loadingPanel.visibility = View.GONE
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateUser()
        recycler_orders.adapter = ordersAdapter
        recycler_orders.layoutManager = LinearLayoutManager(context)
        recycler_orders.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        button_close.setOnClickListener {
            showPasswordDialog(object :
                IDeleteOrders {
                override fun onPasswordSuccessful() {
                    ordersViewModel.deleteOrder()
                }
            }, true)
        }
        initObservers()
        btn_home.setOnClickListener {
            (activity as MainActivity).setCheckedHome()
        }
    }

    private fun showPasswordDialog(listener: IPasswordAction, isDismissible: Boolean) {
        val dialog = PasswordDialogFragment.newInstance(listener, isDismissible)
        childFragmentManager.let { dialog.show(it, "PasswordDialog") }
        dialog.isCancelable = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ordersViewModel.allOrders.observe(this, Observer {
            if (it.isNotEmpty()) {
                llEmptyView.visibility = View.GONE
                cl_orders.visibility = View.VISIBLE
                ordersAdapter.setItems(mapArray(it))
            } else {
                llEmptyView.visibility = View.VISIBLE
                cl_orders.visibility = View.GONE
            }

        })
    }

    private fun mapArray(args: List<MyOrder>): List<Product> {
        val myMap = HashMap<String, Long>()
        var totalSales = ""
        args.forEach { orderData ->
            orderData.items.forEach { product ->
                if (product.additional) {
                    totalSales =
                        args.map { it.total + (product.product.cantidad) }.sum().toString()
                    val myKey = product.product.name.plus(ADDITIONAL)
                    if (myMap.containsKey(myKey)) {
                        var quantity = myMap[myKey] ?: 0L
                        quantity += product.quantity
                        myMap[myKey] = quantity
                    } else {
                        myMap[myKey] = product.quantity
                    }
                } else {
                    totalSales =
                        args.map { it.total + (product.product.cantidad) }.sum().toString()
                    val myKey = product.product.name
                    if (myMap.containsKey(myKey)) {
                        var quantity = myMap[myKey] ?: 0L
                        quantity += product.quantity
                        myMap[myKey] = quantity
                    } else {
                        myMap[myKey] = product.quantity
                    }
                }
            }
        }
        val prefix = txt_total_ventas.context.getString(R.string.sales_day)
        val value = totalSales.toInt().convertToMoney()
        txt_total_ventas.text = "$prefix: $value"
        return myMap.map {
            val product = Product()
            product.Name = it.key
            product.Quantity = it.value
            product
        }
    }
}