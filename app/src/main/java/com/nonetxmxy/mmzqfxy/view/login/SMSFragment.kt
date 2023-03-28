package com.nonetxmxy.mmzqfxy.view.login

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.databinding.FragmentSmsBinding
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.viewmodel.SMSFragViewModel
import com.tuo.customview.VerificationCodeView.InputCompleteListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SMSFragment : BaseFragment<FragmentSmsBinding, SMSFragViewModel>() {

    private val viewModel: SMSFragViewModel by viewModels()

    override fun getViewMode(): SMSFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentSmsBinding =
        FragmentSmsBinding.inflate(inflater, parent, false)

    override fun setLayout() {
        binding.tvPhone.text =
            String.format(getString(R.string.send_vcode_tips), LocalCache.phoneNumber.substring(2))

        binding.vcSms.setInputCompleteListener(object : InputCompleteListener {
            override fun inputComplete() {
                binding.tvContinue.isEnabled = (binding.vcSms.inputContent.length == 4)
            }

            override fun deleteContent() {
                binding.tvContinue.isEnabled = (binding.vcSms.inputContent.length == 4)
            }
        })

        binding.tvSendSms.setLimitClickListener {
            viewModel.smsCodeSent()
        }

        binding.tvSendVoiceCode.setLimitClickListener {
            viewModel.voiceCodeSent()
        }

        binding.tvContinue.setLimitClickListener {
            viewModel.doLogin(binding.vcSms.inputContent)
        }
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.clock.collect {
                binding.tvSendSms.apply {
                    setTextColor(
                        this@SMSFragment.context?.getColor(R.color.gray_999999) ?: Color.BLACK
                    )
                    isEnabled = false
                }
                for (i in it downTo 0) {
                    binding.tvSendSms.text = "$i"
                    if (i > 0) {
                        delay(1000L)
                    }
                }
                binding.tvSendSms.apply {
                    text = getString(R.string.send)
                    isEnabled = true
                    setTextColor(
                        this@SMSFragment.context?.getColor(R.color.gray_532e00) ?: Color.BLACK
                    )
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.voiceClock.collect {
                binding.tvSendVoiceCode.apply {
                    setTextColor(
                        this@SMSFragment.context?.getColor(R.color.gray_999999) ?: Color.BLACK
                    )
                    isEnabled = false
                }
                for (i in it downTo 0) {
                    binding.tvSendVoiceCode.text =
                        String.format(getString(R.string.send_voice_code_on_clock), i)
                    if (i > 0) {
                        delay(1000L)
                    }
                }
                binding.tvSendVoiceCode.apply {
                    text = getString(R.string.send_voice_code)
                    isEnabled = true
                    setTextColor(
                        this@SMSFragment.context?.getColor(R.color.yellow_f6c93d) ?: Color.BLACK
                    )
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.goMain.collect {
                navController.setGraph(R.navigation.main_navigation)
            }
        }
    }
}