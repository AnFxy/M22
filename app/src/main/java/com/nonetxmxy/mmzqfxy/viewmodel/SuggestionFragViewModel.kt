package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import com.nonetxmxy.mmzqfxy.repository.create.BeginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class SuggestionFragViewModel @Inject constructor(
    val beginRepository: IBeginRepository
) : BaseViewModel() {

    var currentIndex = 0

    var picLinkList = ArrayList<String>()

    private val _finishAndClosePage = MutableSharedFlow<Unit>()
    val finishAndClosePage: SharedFlow<Unit> = _finishAndClosePage

    fun uploadSuggestion(content: String) {
        launchUIWithDialog {
            beginRepository.submitSuggestion(content, picLinkList)
            _finishAndClosePage.emit(Unit)
        }
    }
}