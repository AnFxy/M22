package com.nonetxmxy.mmzqfxy.viewmodel

import androidx.lifecycle.viewModelScope
import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.ContactPersonData
import com.nonetxmxy.mmzqfxy.model.OptionShowList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthContactPersonViewModel @Inject constructor(private val repository: IContactPersonAuthRepository) :
    BaseViewModel() {

    sealed class Event {
        object SamePhone : Event()
    }

    private val _optionShowListFlow = MutableStateFlow<OptionShowList?>(null)
    val optionShowListFlow = _optionShowListFlow.asStateFlow()

    private val _pagerEventFlow = MutableSharedFlow<Event>()
    val pagerEventFlow = _pagerEventFlow

    private val _pagerDataFlow = MutableStateFlow(
        ContactPersonData(
            relationshipFirst = ""
        )
    )
    var pagerDataFlow = _pagerDataFlow

    init {
        getPageData()
    }

    private fun getPageData() {
        launchUIWithDialog {
            _optionShowListFlow.emit(repository.getOptionShowList())
            if (LocalCache.contactPersonCredit == 1) {
                _pagerDataFlow.emit(repository.getSubmitContactPerson())
            }
        }
    }

    fun updateContactData(selectContact1: Boolean, phone: String, name: String) {
        if (phone == _pagerDataFlow.value.relationshipFirst || phone == _pagerDataFlow.value.relationshipFirst) {
            viewModelScope.launch {
                _pagerEventFlow.emit(Event.SamePhone)
            }
        } else {
            _pagerDataFlow.value = _pagerDataFlow.value.copy(relationshipFirst = phone)
        }
    }
}
