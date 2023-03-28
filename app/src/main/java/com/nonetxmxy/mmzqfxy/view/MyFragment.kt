package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.databinding.FragmentMyBinding
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.viewmodel.MyFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MyFragment : BaseFragment<FragmentMyBinding, MyFragViewModel>() {

    private val viewModel: MyFragViewModel by viewModels()

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
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.pagerData.collect {
                it?.let { repayMessage ->
                    // 有要还的订单
                    if (repayMessage.OEdZXUBY > 0) {
                        // 判断是否逾期来显示不同风格

                        // TODO 点击后跳转到 还款页面，
                    }
                }
            }
        }
    }
}
