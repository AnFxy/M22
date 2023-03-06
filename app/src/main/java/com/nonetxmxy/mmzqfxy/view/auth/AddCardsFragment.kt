package com.nonetxmxy.mmzqfxy.view.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentAddCardsBinding
import com.nonetxmxy.mmzqfxy.viewmodel.AddCardsFragViewModel

class AddCardsFragment : BaseFragment<FragmentAddCardsBinding, AddCardsFragViewModel>() {

    private val viewModel: AddCardsFragViewModel by viewModels()

    override fun getViewMode(): AddCardsFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentAddCardsBinding =
        FragmentAddCardsBinding.inflate(inflater, parent, false)

    override fun setLayout() {

    }
}
