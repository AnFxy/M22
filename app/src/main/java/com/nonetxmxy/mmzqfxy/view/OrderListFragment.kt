package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.adapters.OrderListAdapter
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.receiveCallBackDataFromLastFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentOrderListBinding
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.view.payback.PayFragment
import com.nonetxmxy.mmzqfxy.viewmodel.OrderListFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderListFragment : BaseFragment<FragmentOrderListBinding, OrderListFragViewModel>() {

    private val viewModel: OrderListFragViewModel by viewModels()

    private val adapter: OrderListAdapter by lazy {
        OrderListAdapter({

            if (!orderArgs.isTab) {
                navController.popBackStack()
            }

            navController.navigate(
                R.id.product_list_navigation,
                null,
                NavOptions.Builder()
                    .setRestoreState(true)
                    .setPopUpTo(
                        R.id.productListFragment,
                        false,
                        true
                    )
                    .build()
            )
        }, {
            navController.navigate(OrderListFragmentDirections.actionOrderListFragmentToPayNavigation())
        })
    }

    private val orderArgs: OrderListFragmentArgs by navArgs()

    override fun getViewMode(): OrderListFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentOrderListBinding =
        FragmentOrderListBinding.inflate(inflater, parent, false)

    override fun setLayout() {

        binding.mRefresh.setOnRefreshListener {
            viewModel.requestOrders()
        }

        if (!orderArgs.isTab) {
            binding.tvTitle.text = "Mi préstamo"
        }

        binding.rvOrder.apply {
            layoutManager = LinearLayoutManager(this@OrderListFragment.context)
            adapter = this@OrderListFragment.adapter
        }

        binding.viewBg.setVisible(orderArgs.isTab)
        binding.mToolbar.findViewById<ImageView>(R.id.iv_back).apply {
            setVisible(!orderArgs.isTab)
            setLimitClickListener {
                navController.popBackStack()
            }
        }

        receiveCallBackDataFromLastFragment<Boolean>(PayFragment.BACK) {
            viewModel.requestOrders()
        }
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orders.collect {
                if (orderArgs.isTab) {
                    adapter.orders = it.filter { item -> item.snbrREWru in listOf(5) }
                } else {
                    adapter.orders = it
                }
            }
        }
    }
}
