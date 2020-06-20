package com.gforeroc.dondeorlando.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.ui.base.IDeleteOrders
import com.gforeroc.dondeorlando.ui.base.IPasswordAction
import com.gforeroc.dondeorlando.ui.base.IShowOrders
import com.gforeroc.dondeorlando.data.models.Product
import com.gforeroc.dondeorlando.domain.myOrders.MyOrder
import com.gforeroc.dondeorlando.ui.orders.adapter.OrdersAdapter
import com.gforeroc.dondeorlando.utils.PasswordDialogFragment
import com.gforeroc.dondeorlando.viewmodels.OrdersViewModel
import kotlinx.android.synthetic.main.fragment_orders.*
import org.koin.android.viewmodel.ext.android.viewModel


class OrdersFragment : Fragment() {

    private val ordersAdapter = OrdersAdapter()
    private val ordersViewModel: OrdersViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    fun validateUser() {
        showPasswordDialog(object :
            IShowOrders {
            override fun onPasswordSuccessful() {
                rl_orders.visibility = View.VISIBLE
            }
        }, false)
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
    }

    private fun showPasswordDialog(listener: IPasswordAction, isDismissible: Boolean) {
        val dialog = PasswordDialogFragment.newInstance(listener, isDismissible)
        childFragmentManager.let { dialog.show(it, "PasswordDialog") }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ordersViewModel.allOrders.observe(this, Observer {
            ordersAdapter.setItems(mapArray(it))
        })
    }

    private fun mapArray(args: List<MyOrder>): List<Product> {
        val myMap = HashMap<String, Long>()
        var totalSales = ""
        args.forEach { orderData ->
            orderData.items.forEach { product ->
                if (product.additional) {
                    totalSales =
                        args.map { it.total * (product.quantity) }.sum().toString()
                    val additional = " --Adicional"
                    val myKey = product.product.name.plus(additional)
                    if (myMap.containsKey(myKey)) {
                        var quantity = myMap[myKey] ?: 0L
                        quantity += product.quantity
                        myMap[myKey] = quantity
                    } else {
                        myMap[myKey] = product.quantity
                    }
                } else {
                    totalSales =
                        args.map { it.total * (product.quantity) }.sum().toString()
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
        txt_total_ventas.text = "$prefix: $totalSales"
        return myMap.map {
            val product = Product()
            product.Name = it.key
            product.Quantity = it.value
            product
        }
    }
}