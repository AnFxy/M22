package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentSuggestionBinding
import com.nonetxmxy.mmzqfxy.viewmodel.SuggestionFragViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuggestionsFragment : BaseFragment<FragmentSuggestionBinding, SuggestionFragViewModel>() {

    private val viewModel : SuggestionFragViewModel by viewModels()

    override fun getViewMode(): SuggestionFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentSuggestionBinding =
        FragmentSuggestionBinding.inflate(inflater, parent, false)

    override fun setLayout() {

    }
}