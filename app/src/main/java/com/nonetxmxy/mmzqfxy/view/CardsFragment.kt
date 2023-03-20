package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nonetxmxy.mmzqfxy.adapters.CardsAdapter
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentCardsBinding
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.viewmodel.CardsFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardsFragment : BaseFragment<FragmentCardsBinding, CardsFragViewModel>() {

    private val viewModel: CardsFragViewModel by viewModels()

    private val adapter: CardsAdapter by lazy {
        CardsAdapter {
            navController.navigate(CardsFragmentDirections.actionCardsFragmentToAddCardsFragment())
        }
    }

    override fun getViewMode(): CardsFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentCardsBinding =
        FragmentCardsBinding.inflate(inflater, parent, false)

    override fun setLayout() {

        binding.rvCards.adapter = this.adapter
        binding.tvAddCards.apply {
            setLimitClickListener {
                setVisible(false)
                navController.navigate(CardsFragmentDirections.actionCardsFragmentToAddCardsFragment())
            }
        }
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cards.collect {
                adapter.orders = it
                binding.tvAddCards.setVisible(it.isNotEmpty())
            }
        }
    }
}
