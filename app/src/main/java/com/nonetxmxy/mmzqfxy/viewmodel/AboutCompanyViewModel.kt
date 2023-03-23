package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class AboutCompanyViewModel @Inject constructor(
    private val beginRepository: IBeginRepository
) : BaseViewModel() {

    private val _goLogin = MutableSharedFlow<Unit>()
    val goLogin: SharedFlow<Unit> = _goLogin

    fun doLogout() {
        launchUIWithDialog {
            beginRepository.doLogout()
            _goLogin.emit(Unit)
        }
    }
}
