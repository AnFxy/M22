package com.nonetxmxy.mmzqfxy.view.login

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.ToastUtils
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.databinding.FragmentLoginBinding
import com.nonetxmxy.mmzqfxy.model.LoginType
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.viewmodel.LoginFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginFragViewModel>() {

    private val viewModel: LoginFragViewModel by viewModels()

    override fun getViewMode(): LoginFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentLoginBinding =
        FragmentLoginBinding.inflate(inflater, parent, false)

    override fun setLayout() {
        binding.editNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.updateInputNumber(p0.toString())
            }
        })

        binding.ivClearText.setLimitClickListener {
            binding.editNumber.setText("")
            viewModel.updateInputNumber("")
        }

        binding.tvContinue.setLimitClickListener {
            val privacyChecked = binding.cbPrivacy.isChecked
            if (!privacyChecked) {
                ToastUtils.showShort(getText(R.string.agree_tips))
            } else {
                viewModel.beginToLogin()
            }
        }
    }

    override fun setObserver() {
        lifecycleScope.launch {
            viewModel.inputNumber.collect {
                binding.tvContinue.isEnabled = it.length >= 10
                binding.ivClearText.setVisible(it.isNotEmpty())
            }
        }

        lifecycleScope.launch {
            viewModel.goPage.collect {
                LocalCache.phoneNumber = viewModel.inputNumber.value
                when(it) {
                    LoginType.MAIN -> navController.navigate(R.id.goMain)
                    LoginType.SMS -> navController.navigate(LoginFragmentDirections.actionLoginFragmentToSMSFragment())
                }
            }
        }
    }
}
