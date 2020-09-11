package com.gforeroc.dondeorlando.ui.orders

import android.graphics.Rect
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import kotlin.collections.set

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
        }, false, isVisibleCheck = false, isVisibleBack = true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sales, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.close_sales -> showPinPad()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fillCourtesy(products: List<Product>) {
        if (products.isEmpty()) {
            rv_courtesy.visibility = View.GONE
            tvCourtesy.visibility = View.GONE
        } else {
            val courtesyAdapter = OrdersAdapter()
            courtesyAdapter.setItems(products)
            rv_courtesy.adapter = courtesyAdapter
            rv_courtesy.layoutManager = GridLayoutManager(context, 2)
            rv_courtesy.addItemDecoration(MarginItemDecoration(12))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateUser()
        setHasOptionsMenu(true)
        recycler_orders.adapter = ordersAdapter
        recycler_orders.layoutManager = GridLayoutManager(context, 2)
        recycler_orders.addItemDecoration(MarginItemDecoration(12));
        initObservers()
        btn_home.setOnClickListener {
            (activity as MainActivity).setCheckedHome()
        }
    }

    private fun showPinPad() {
        showPasswordDialog(object :
            IDeleteOrders {
            override fun onPasswordSuccessful() {
                ordersViewModel.deleteOrder()
            }
        }, true, isVisibleCheck = true, isVisibleBack = false)
    }

    private fun showPasswordDialog(
        listener: IPasswordAction,
        isDismissible: Boolean,
        isVisibleCheck: Boolean,
        isVisibleBack: Boolean
    ) {
        val dialog = PasswordDialogFragment.newInstance(
            listener,
            isDismissible,
            isVisibleCheck,
            isVisibleBack
        )
        childFragmentManager.let { dialog.show(it, "PasswordDialog") }
        dialog.isCancelable = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ordersViewModel.allOrders.observe(this, Observer {
            if (it.isNotEmpty()) {
                llEmptyView.visibility = View.GONE
                cl_orders.visibility = View.VISIBLE
                processOrders(it)
            } else {
                llEmptyView.visibility = View.VISIBLE
                cl_orders.visibility = View.GONE
            }

        })
    }

    private fun processOrders(orders: List<MyOrder>) {
        val courtesy = orders.filter { it.Courtesy }
        val paid = orders.filter { !it.Courtesy }

        val sales = calculateTotal(paid).toInt().convertToMoney()
        val courtesySales = calculateTotal(courtesy).toInt().convertToMoney()
        fillCourtesy(mapArray(courtesy))
        fillOrdersPaid(mapArray(paid))
        val prefixSales = txt_total_ventas.context.getString(R.string.sales_day)
        val prefixCourtesy = txt_total_ventas.context.getString(R.string.courtesy_day)
        txt_total_ventas.text = "($prefixSales $sales) - ($prefixCourtesy $courtesySales)"

    }

    private fun fillOrdersPaid(products: List<Product>) {
        if (products.isEmpty()) {
            tvSales.visibility = View.GONE
            recycler_orders.visibility = View.GONE
        } else
            ordersAdapter.setItems(products)
    }

    private fun mapArray(args: List<MyOrder>): List<Product> {
        val myMap = HashMap<String, Long>()
        args.forEach { orderData ->
            orderData.items.forEach { product ->
                if (product.additional) {
                    val myKey = product.product.name.plus(ADDITIONAL)
                    if (myMap.containsKey(myKey)) {
                        var quantity = myMap[myKey] ?: 0L
                        quantity += product.quantity
                        myMap[myKey] = quantity
                    } else {
                        myMap[myKey] = product.quantity
                    }
                } else {
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
        return myMap.map {
            val product = Product()
            product.Name = it.key
            product.Quantity = it.value
            product
        }.sortedBy { it.Name }
    }

    private fun calculateTotal(args: List<MyOrder>): String {
        var totalSales = "0"
        args.forEach { orderData ->
            orderData.items.forEach { product ->
                totalSales = if (product.additional) {
                    args.map { it.total + (product.product.cantidad) }.sum().toString()
                } else {
                    args.map { it.total + (product.product.cantidad) }.sum().toString()
                }
            }
        }
        return totalSales
    }
}

class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            top = spaceHeight
            left = spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}