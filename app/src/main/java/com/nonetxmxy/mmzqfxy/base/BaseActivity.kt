package com.nonetxmxy.mmzqfxy.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.nonetxmxy.mmzqfxy.R
import kotlinx.coroutines.launch

abstract class BaseActivity<VB : ViewBinding, T : BaseViewModel> : AppCompatActivity() {

    // viewBinding 获取
    private lateinit var _binding: VB
    protected val binding get() = _binding

    // viewModel 获取
    private val mviewModel: T by lazy { getViewMode() }

    // 网络加载圈
    val requestDataLoadDialog: RxDialogSet by lazy {
        RxDialogSet.provideDialog(this, R.layout.dia_loading)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = getViewBinding()
        setContentView(_binding.root)

        // 常规设置
        initGenerateData()
        initPageLayout()
        setListener()
    }

    // 监听 加载圈的状态
    private fun initGenerateData() {
        lifecycleScope.launch {
            mviewModel._loadingEvent.collect {
                updateLoadingViewStatus(it)
            }
        }
    }

    // 更新加载圈的状态，子类也可用
    fun updateLoadingViewStatus(isLoading: Boolean) {
        if (isLoading) {
            if (!requestDataLoadDialog.isShowing)
                requestDataLoadDialog.show()
        } else {
            if (requestDataLoadDialog.isShowing)
                requestDataLoadDialog.dismiss()
        }
    }

    abstract fun getViewMode(): T

    abstract fun getViewBinding(): VB

    // 初始化 页面的布局
    abstract fun initPageLayout()

    // 设置监听器 包括点击监听，页面viewModel状态修改监听
    abstract fun setListener()
}