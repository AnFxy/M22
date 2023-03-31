package com.nonetxmxy.mmzqfxy.view.payback

import android.graphics.Typeface
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.SpanUtils.ALIGN_CENTER
import com.bumptech.glide.Glide
import com.nonetxmxy.mmzqfxy.MainActivity
import com.nonetxmxy.mmzqfxy.MainActivityViewModel
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.RxDialogSet
import com.nonetxmxy.mmzqfxy.databinding.FragmentPayCodeBinding
import com.nonetxmxy.mmzqfxy.model.PayCodeMessage
import com.nonetxmxy.mmzqfxy.tools.CopyUtil
import com.nonetxmxy.mmzqfxy.tools.jinE
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.viewmodel.PayCodeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayCodeFragment : BaseFragment<FragmentPayCodeBinding, PayCodeViewModel>() {

    private val viewModel: PayCodeViewModel by viewModels()

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    private val args: PayCodeFragmentArgs by navArgs()

    override fun getViewMode(): PayCodeViewModel = viewModel

    private val questionRxDialogSet: RxDialogSet? by lazy {
        context?.let {
            val dialog = RxDialogSet.provideDialog(it, R.layout.dia_question_pay_code)
            dialog.setViewState<TextView>(R.id.tv_i_know) {
                setLimitClickListener {
                    dialog.dismiss()
                }
            }
            dialog
        }
    }

    private val payCodeLimitRxDialogSet: RxDialogSet? by lazy {
        context?.let {
            val dialog = RxDialogSet.provideDialog(it, R.layout.dia_pay_code_limit)
            dialog.setViewState<TextView>(R.id.tv_i_know) {
                setLimitClickListener {
                    dialog.dismiss()
                    navController.popBackStack()
                    mainViewModel.sendRefreshEvent()
                }
            }.setViewState<TextView>(R.id.tv_think_again) {
                setLimitClickListener {
                    dialog.dismiss()
                }
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentPayCodeBinding =
        FragmentPayCodeBinding.inflate(inflater, parent, false)

    override fun setLayout() {
        viewModel.getPayCodeData(
            payWayCode = args.payWayCode,
            mainId = args.orderMainId,
            sonId = args.orderSonId,
            payType = args.payType
        )

        activity?.let {
            (it as MainActivity).specialOnBackPressed = {
                payCodeLimitRxDialogSet?.show()
            }
        }

        binding.tvCopy.setLimitClickListener {
            CopyUtil.copyToClipboard(viewModel.pagerData.value?.QWZRFcNqe ?: "")
        }
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.pagerData.collect {
                it?.let {
                    updatePage(it)
                }
            }
        }
    }

    private fun updatePage(payCodeMessage: PayCodeMessage) {
        binding.tvPayCode.text = payCodeMessage.QWZRFcNqe
        binding.tvAmount.text = payCodeMessage.giwxW.jinE()
        binding.tvProName.text = args.proName

        val htmlBuilder = SpannableStringBuilder(Html.fromHtml(getString(R.string.spei_des)))
        htmlBuilder.setSpan(StyleSpan(Typeface.BOLD), 11, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvSpiTitle.text = htmlBuilder

        val builder =
            SpanUtils.with(binding.tvSpiContent).appendImage(R.mipmap.wenhao).setClickSpan(
                object : ClickableSpan() {
                    override fun onClick(p0: View) {
                        questionRxDialogSet?.show()
                    }
                }
            ).setVerticalAlign(ALIGN_CENTER).append("  ")
                .append(
                    String.format(
                        getString(R.string.spei_warning),
                        args.payWayName
                    )
                ).create()

        binding.tvSpiContent.text = builder

        Glide.with(this).load(payCodeMessage.munR).into(binding.ivCode)
        Glide.with(this).load(args.payWayLogo).into(binding.ivPayWay)
        // 控件可见性
        binding.containerNotSpeiWarning.setVisible(args.payWayName != "SPEI")
        binding.containerCodeImage.setVisible(args.payWayName != "SPEI")
    }
}