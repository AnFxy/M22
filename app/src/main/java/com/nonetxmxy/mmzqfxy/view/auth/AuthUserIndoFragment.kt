package com.nonetxmxy.mmzqfxy.view.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.adapters.GridLayoutManagerItemDecoration
import com.nonetxmxy.mmzqfxy.adapters.SourceIncomeAdapter
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentAuthUserInfoBinding
import com.nonetxmxy.mmzqfxy.model.SelfData
import com.nonetxmxy.mmzqfxy.viewmodel.AuthUserIndoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthUserIndoFragment : BaseFragment<FragmentAuthUserInfoBinding, AuthUserIndoViewModel>() {

    private val viewModel: AuthUserIndoViewModel by viewModels()

    private val sourceIncomeAdapter by lazy {
        val adapter = SourceIncomeAdapter()
        adapter.setOnItemClickListener { _, _, position ->
            if (position == adapter.currentIndex) return@setOnItemClickListener
            val oldIndex = adapter.currentIndex
            adapter.currentIndex = position
            adapter.notifyItemChanged(oldIndex)
            adapter.notifyItemChanged(position)
        }
        adapter
    }

    override fun getViewMode() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ) = FragmentAuthUserInfoBinding.inflate(inflater, parent, false)

    override fun FragmentAuthUserInfoBinding.setLayout() {

        toolBar.setupWithNavController(navController)
        toolBar.setNavigationIcon(R.mipmap.fanhui)

        includeAuthTitle.image.setImageResource(R.mipmap.jinbi1)

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.addItemDecoration(GridLayoutManagerItemDecoration(10f, 38f))
        recyclerView.adapter = sourceIncomeAdapter

        includeAuthBottom.enviarBtn.setOnClickListener {
            checkData()
        }
    }

    private fun checkData(): Boolean {
        return false
    }

    override fun FragmentAuthUserInfoBinding.setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.optionShowListFlow.collect {
                    if (it == null) return@collect
                    commonSelect1.setOptionShowList(it.marryStatus)
                    commonSelect2.setOptionShowList(it.marryStatus)
                    commonSelect3.setOptionShowList(it.marryStatus)
                    sourceIncomeAdapter.setList(it.marryStatus)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagerDataFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect {
                    updatePage(it)
                }
        }
    }

    private fun updatePage(data: SelfData) {
        binding.apply {
            commonSelect1.selectContent = data.educationLevelShow
            commonSelect2.selectContent = data.childrenTotalShow
            commonSelect3.selectContent = "${data.familyProvince}/${data.familyCity}"
            input1.inputContent = "${data.familyProvince}/${data.familyCity}"
        }
    }
}