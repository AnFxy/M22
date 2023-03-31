package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.Utils
import com.nonetxmxy.mmzqfxy.MainActivityViewModel
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.databinding.FragmentMyBinding
import com.nonetxmxy.mmzqfxy.model.OrderMessage
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.viewmodel.MyFragViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFragment : BaseFragment<FragmentMyBinding, MyFragViewModel>() {

    private val viewModel: MyFragViewModel by viewModels()

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    override fun getViewMode(): MyFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentMyBinding =
        FragmentMyBinding.inflate(inflater, parent, false)

    override fun setLayout() {

        binding.tvPhone.text = LocalCache.phoneNumber.let {
            if (it.length == 12)
                it.substring(2)
            else
                it
        }

        binding.containerHis.setLimitClickListener {
            navController.navigate(MyFragmentDirections.actionMyFragmentToOrderListFragment(false))
        }

        binding.containerCard.setLimitClickListener {
            navController.navigate(MyFragmentDirections.actionMyFragmentToCardsFragment())
        }

        binding.containerInfo.setLimitClickListener {
            navController.navigate(MyFragmentDirections.actionMyFragmentToAllAuthFragment())
        }

        binding.containerUs.setLimitClickListener {
            navController.navigate(MyFragmentDirections.actionMyFragmentToAboutCompanyFragment())
        }

        binding.containerFeedback.setLimitClickListener {
            navController.navigate(MyFragmentDirections.actionMyFragmentToSuggestionsFragment())
        }

        binding.mRefresh.setOnRefreshListener {
            viewModel.getPageData()
        }

        binding.tvOrderDes.setLimitClickListener {
            LocalCache.currentProCode = viewModel.currentItem.value?.piKSfRNing ?: ""
            navController.navigate(MyFragmentDirections.actionMyFragmentToPayNavigation())
        }

        updatePage(viewModel.currentItem.value)
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.currentItem.collect {
                updatePage(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mainViewModel.refreshPage.collect {
                viewModel.getPageData()
            }
        }
    }

    private fun updatePage(orderMessage: OrderMessage?) {
        val desStr =
            if ((orderMessage?.JMgRrdrv ?: 0) >= 0)
                getString(R.string.us_order_des)
            else
                getString(R.string.us_order_des_not)

        binding.tvOrderDes.setVisible(orderMessage != null)
        binding.tvOrderDes.text = desStr

        binding.tvOrderDes.setTextColor(
            if ((orderMessage?.JMgRrdrv ?: 0) >= 0)
                Utils.getApp().getColor(R.color.color_00b938)
            else
                Utils.getApp().getColor(R.color.red_f06047)
        )

        binding.tvOrderDes.setBackgroundColor(
            if ((orderMessage?.JMgRrdrv ?: 0) >= 0)
                Utils.getApp().getColor(R.color.color_00b938_10)
            else
                Utils.getApp().getColor(R.color.red_f06047_10)
        )
    }
}
