package com.nonetxmxy.mmzqfxy.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.tools.setMenuAndNavLimitClickListener
import kotlinx.coroutines.launch

abstract class BaseFragment<T : ViewBinding, VB : BaseViewModel> : Fragment() {

    lateinit var binding: T

    var isHiddenStatus = false

    lateinit var navController: NavController


    // viewModel 获取
    private val mviewModel: VB by lazy {
        getViewMode()
    }

    // 网络加载圈
    private val requestDataLoadDialog: RxDialogSet? by lazy {
        context?.let {
            RxDialogSet.provideDialog(it, R.layout.dia_loading)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            mviewModel._loadingEvent.collect {
                updateLoadingViewStatus(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getViewBinding(inflater, container)

        //增加状态栏高度
        context?.run {
            binding.root.findViewById<Toolbar>(R.id.m_toolbar)
                ?.apply {
                    val resourceId: Int =
                        resources.getIdentifier("status_bar_height", "dimen", "android")
                    if (!isHiddenStatus) {
                        setPadding(0, this.resources.getDimensionPixelSize(resourceId), 0, 0)
                    }

                    setMenuAndNavLimitClickListener({
                        activity?.onBackPressed()
                    }, {
                        activity?.let {
//                            SuperHandleUtils.showCustomerServiceDialog(
//                                it,
//                                BaseCache.customerServiceNumber
//                            )
                        }
                    })
                }
        }

        val refreshIt = binding.root.findViewById<SwipeRefreshLayout>(R.id.m_refresh)
        refreshIt?.setColorSchemeResources(R.color.yellow_f5c83b)

        // 加载圈监听
        viewLifecycleOwner.lifecycleScope.launch {
            mviewModel._closeLoading.collect {
                val refresh = binding.root.findViewById<SwipeRefreshLayout>(R.id.m_refresh)
                if (refresh?.isRefreshing == true) {
                    refresh.isRefreshing = false
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.setLayout()
        binding.setObserver()
        setLayout()
        setObserver()
    }

    // 更新加载圈的状态，子类也可用
    private fun updateLoadingViewStatus(isLoading: Boolean) {
        if (isLoading) {
            if (requestDataLoadDialog?.isShowing == false)
                requestDataLoadDialog?.show()
        } else {
            if (requestDataLoadDialog?.isShowing == true)
                requestDataLoadDialog?.dismiss()
        }
    }

    abstract fun getViewMode(): VB

    abstract fun getViewBinding(inflater: LayoutInflater, parent: ViewGroup?): T

    open fun setLayout() {}

    open fun T.setLayout() {}

    open fun setObserver() {}

    open fun T.setObserver() {}
}