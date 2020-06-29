package com.gforeroc.dondeorlando.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.gforeroc.dondeorlando.BR
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.domain.NewOrder
import com.gforeroc.dondeorlando.domain.ProductOrder
import com.gforeroc.dondeorlando.ui.PageAdapter
import com.gforeroc.dondeorlando.ui.base.IConfirmOrder
import com.gforeroc.dondeorlando.utils.OnProductOrderAdded
import com.gforeroc.dondeorlando.utils.OrderCarDialogFragment
import com.gforeroc.dondeorlando.utils.OrdersAction
import com.gforeroc.dondeorlando.utils.ZoomOutPageTransformer
import com.gforeroc.dondeorlando.viewmodels.OrdersViewModel
import es.dmoral.toasty.Toasty
import ir.androidexception.andexalertdialog.AndExAlertDialog
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_orders.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(), OnProductOrderAdded,
    IConfirmOrder {

    private var mContext: Context? = null
    private lateinit var newOrder: NewOrder

    private val ordersViewModel: OrdersViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.setVariable(BR.currentOrder, newOrder)
        return binding.root
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
        viewpager.setPageTransformer(true, ZoomOutPageTransformer())
        tabCategories.setupWithViewPager(viewpager)
        btn_car.setOnClickListener { checkOrderCar() }
        initObservers()
    }

    override fun setProduct(product: ProductOrder) {
        newOrder.addProduct(product)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun checkOrderCar() {
        if (canFinishOrder()) {
            newOrder.setDate()
            newOrder.calculateTotals()
            val dialogCar: DialogFragment = OrderCarDialogFragment.newInstance(newOrder, this)
            childFragmentManager.let { dialogCar.show(it, "OrderCarDialogFragment") }
        } else
            showWarningDialog()
    }

    private fun canFinishOrder(): Boolean {
        return newOrder.items.isNotEmpty()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cancel_order -> findNavController().popBackStack()
            R.id.finish_order -> checkOrderCar()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun confirmOrderListener() {
        ordersViewModel.sendOrder(newOrder)
    }

    private fun initObservers() {
        ordersViewModel.getError().observe(this) { result ->
            context?.let {
                when (result) {
                    OrdersAction.SAVEORDER_SUCCESS -> {
                        Toasty.success(
                            it,
                            "Venta registrada exitosamente!",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    }

                    OrdersAction.SAVEORDER_ERROR -> {
                        Toasty.success(
                            it,
                            "Hubo un error al guardar la orden",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()

                    }
                    else -> {
                        Toasty.info(it, "Intenta nuevamente", Toast.LENGTH_SHORT, true).show()
                    }
                }
            }
        }

        ordersViewModel.getLoading().observe(this) { isLoading ->
            if (isLoading != null) {
                if (isLoading) {
                    loadingPanelHome.visibility = View.VISIBLE
                } else {
                    loadingPanelHome.visibility = View.GONE
                }
            }
        }
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



