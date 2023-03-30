package com.nonetxmxy.mmzqfxy.view.auth

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nonetxmxy.mmzqfxy.MainActivity
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.adapters.MainAppAdapter
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.base.callBackDataWhenDestroyed
import com.nonetxmxy.mmzqfxy.databinding.FragmentUnderReviewBinding
import com.nonetxmxy.mmzqfxy.model.OrderMessage
import com.nonetxmxy.mmzqfxy.model.ProductsBean
import com.nonetxmxy.mmzqfxy.tools.CommonUtil
import com.nonetxmxy.mmzqfxy.tools.days
import com.nonetxmxy.mmzqfxy.tools.jinE
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.viewmodel.UnderReviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UnderReviewFragment : BaseFragment<FragmentUnderReviewBinding, UnderReviewViewModel>() {

    private val viewModel: UnderReviewViewModel by viewModels()

    private val mainAppAdapter: MainAppAdapter by lazy {
        MainAppAdapter()
    }

    companion object {
        val BACK = "under_review_back"
    }

    override fun getViewMode(): UnderReviewViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentUnderReviewBinding =
        FragmentUnderReviewBinding.inflate(inflater, parent, false)

    override fun setLayout() {
        binding.rvApp.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mainAppAdapter
        }

        binding.mRefresh.setOnRefreshListener {
            viewModel.getPageData()
        }

        mainAppAdapter.onItemClick = {
            CommonUtil.goGooglePlay(activity, downloadUrl = it.XJUpUIdhWR, _packageName = it.ufKPA)
        }

        hideAndShowHomePage()

        activity?.let {
            (it as MainActivity).specialOnBackPressed = {
                callBackDataWhenDestroyed(BACK, true)
            }
        }
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.proData.collect {
                it?.let { bean ->
                    updatePage(bean)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.appsData.collect {
                mainAppAdapter.datas = it
                binding.dividerRv.setVisible(it.isNotEmpty())
            }
        }
    }

    private fun updatePage(productsBean: OrderMessage) {
        when(productsBean.snbrREWru) {
            0, 1, 2, 3 -> {
                binding.ivStatus.setImageResource(R.mipmap.shenhezhong)
                binding.tvStatus.text = getString(R.string.on_reviewing)
                binding.tvStatus.setTextColor(Color.parseColor("#FFA700"))
                binding.tvStatusDes.text = getString(R.string.on_reviewing_des)
            }
            4 -> {
                binding.ivStatus.setImageResource(R.mipmap.shenqing)
                binding.tvStatus.text = getString(R.string.reviewing_pass)
                binding.tvStatus.setTextColor(Color.parseColor("#00B938"))
                binding.tvStatusDes.text = getString(R.string.reviewing_pass_des)
            }
            6 -> {
                binding.ivStatus.setImageResource(R.mipmap.shenhebutongguo)
                binding.tvStatus.text = getString(R.string.refused)
                binding.tvStatus.setTextColor(Color.parseColor("#F06047"))
                binding.tvStatusDes.text = getString(R.string.refused_des)
            }
            else -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(500)
                    callBackDataWhenDestroyed(BACK, true)
                }
            }
        }

        Glide.with(this).load(productsBean.EgjWfY).into(binding.ivLogo)
        binding.tvProName.text = productsBean.qkpPjr
        binding.tvLoanAmount.text = productsBean.fHTnifvEC.jinE()
        binding.tvLoanDays.text = productsBean.nhgL.days()
        binding.tvLoanDate.text = CommonUtil.timeLongToDate(productsBean.JuRZheDh.toLong())

        // 隐藏控件
        binding.containerMessage.setVisible(productsBean.snbrREWru != 6)
        binding.dividerRv.setVisible(productsBean.snbrREWru != 6)
        binding.rvApp.setVisible(productsBean.snbrREWru != 6)
    }

    private fun hideAndShowHomePage() {
        binding.rvApp.setVisible(LocalCache.isLogged && !LocalCache.isSAccount)
        binding.dividerRv.setVisible(LocalCache.isLogged && !LocalCache.isSAccount)
    }
}