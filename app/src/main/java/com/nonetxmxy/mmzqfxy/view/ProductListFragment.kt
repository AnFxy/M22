package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nonetxmxy.mmzqfxy.adapters.MainAdapter
import com.nonetxmxy.mmzqfxy.adapters.MainAppAdapter
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.base.receiveCallBackDataFromLastFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentProductListBinding
import com.nonetxmxy.mmzqfxy.model.ProductsBean
import com.nonetxmxy.mmzqfxy.tools.*
import com.nonetxmxy.mmzqfxy.view.auth.UnderReviewFragment
import com.nonetxmxy.mmzqfxy.viewmodel.ProductListFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentProductListBinding, ProductListFragViewModel>() {

    private val viewModel: ProductListFragViewModel by viewModels()

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter()
    }

    private val mainAppAdapter: MainAppAdapter by lazy {
        MainAppAdapter()
    }

    override fun getViewMode(): ProductListFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentProductListBinding =
        FragmentProductListBinding.inflate(inflater, parent, false)

    override fun FragmentProductListBinding.setLayout() {

        binding.rvPro.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mainAdapter
        }

        binding.rvApp.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mainAppAdapter
        }

        binding.tvApply.setLimitClickListener {
            if (LocalCache.isLogged) {
                viewModel.startNextPageFlow(viewModel.products.value[0])
            } else {
                navController.navigate(ProductListFragmentDirections.actionProductListFragmentToLoginNavigation())
            }
        }

        binding.mRefresh.setOnRefreshListener {
            viewModel.getConfig()
        }

        mainAdapter.onItemClick = {
            LocalCache.currentProCode = it.ANddPfvNno
            viewModel.startNextPageFlow(it)
        }

        mainAppAdapter.onItemClick = {
            CommonUtil.goGooglePlay(activity, downloadUrl = it.XJUpUIdhWR, _packageName = it.ufKPA)
        }

        receiveCallBackDataFromLastFragment<Boolean>(UnderReviewFragment.BACK) {
            viewModel.getConfig()
        }

        hideAndShowHomePage()
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.products.collect {
                if (it.isNotEmpty()) {
                    setSingleProductView(it[0])
                }
                mainAdapter.datas = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.apps.collect {
                mainAppAdapter.datas = it
                binding.dividerRv.setVisible(it.isNotEmpty())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navDirections.collect {
                navController.navigate(it)
            }
        }
    }

    private fun setSingleProductView(product: ProductsBean) {
        Glide.with(this).load(product.MScSWMj).into(binding.ivLogo)
        binding.tvTitle.text = product.eFV
        binding.tvAmount.text = product.FCmiye.toDouble().jinE()
        binding.tvDays.text = product.OJjgAEGVuO.toInt().days()
        binding.tvRate.text = ">=${product.edVaOsmgaab}"
    }

    private fun hideAndShowHomePage() {
        binding.bgSingle.setVisible(LocalCache.isSAccount || !LocalCache.isLogged)
        binding.bgPlus.setVisible(LocalCache.isLogged && !LocalCache.isSAccount)
        binding.toolbarTitle.setVisible(LocalCache.isSAccount || !LocalCache.isLogged)
        binding.ivBanner.setVisible(LocalCache.isSAccount || !LocalCache.isLogged)
        binding.containerHome.setVisible(LocalCache.isSAccount || !LocalCache.isLogged)
        binding.rvApp.setVisible(LocalCache.isLogged && !LocalCache.isSAccount)
        binding.dividerRv.setVisible(LocalCache.isLogged && !LocalCache.isSAccount)
        binding.rvPro.setVisible(LocalCache.isLogged && !LocalCache.isSAccount)
    }
}
