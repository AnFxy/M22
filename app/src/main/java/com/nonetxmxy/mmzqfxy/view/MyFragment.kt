package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.databinding.FragmentMyBinding
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.viewmodel.MyFragViewModel
import dagger.hilt.android.AndroidEntryPoint

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
    }
}
