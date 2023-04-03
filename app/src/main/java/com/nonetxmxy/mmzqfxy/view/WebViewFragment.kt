package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.fragment.app.viewModels
import com.nonetxmxy.mmzqfxy.BuildConfig
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentWebViewBinding
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.viewmodel.WebViewViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WebViewFragment @Inject constructor() :
    BaseFragment<FragmentWebViewBinding, WebViewViewModel>() {

    private val viewModel: WebViewViewModel by viewModels()

    override fun getViewMode(): WebViewViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentWebViewBinding =
        FragmentWebViewBinding.inflate(inflater, parent, false)

    override fun setLayout() {
        binding.wbLoad.apply {
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    binding.pbWebView.apply {
                        progress = newProgress
                        setVisible(newProgress != 100)
                    }
                }
            }
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.javaScriptEnabled = true
            settings.setSupportZoom(false)
            settings.builtInZoomControls = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.domStorageEnabled = true
            loadUrl(BuildConfig.PRAVICY)
        }
    }
}