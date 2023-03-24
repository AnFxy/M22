package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import com.nonetxmxy.mmzqfxy.tools.CommonUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginFragViewModel @Inject constructor(
    private val beginRepository: IBeginRepository
) : BaseViewModel() {

    val inputNumber = MutableStateFlow<String>("")

    val goPage = MutableSharedFlow<Unit>()

    fun updateInputNumber(number: String) {
        inputNumber.value = number
    }

    fun beginToLogin() {
        launchUIWithDialog {
            goPage.emit(Unit)
        }
    }
}
