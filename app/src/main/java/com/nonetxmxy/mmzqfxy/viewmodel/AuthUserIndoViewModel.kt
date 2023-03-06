package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.AdministrativeData
import com.nonetxmxy.mmzqfxy.model.OptionShowList
import com.nonetxmxy.mmzqfxy.model.SelfData
import com.nonetxmxy.mmzqfxy.repository.IUserAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.joinAll
import javax.inject.Inject

@HiltViewModel
class AuthUserIndoViewModel @Inject constructor(private val repository: IUserAuthRepository) :
    BaseViewModel() {

    sealed class PagerEvent {
        object UpdatePageView : PagerEvent()
        object GoWorkPage : PagerEvent()
        object Finsh : PagerEvent()
    }

    private val _optionShowListFlow = MutableStateFlow<OptionShowList?>(null)
    val optionShowListFlow = _optionShowListFlow.asStateFlow()

    private val _administrativeListFlow = MutableStateFlow<AdministrativeData?>(null)
    val administrativeListFlow = _administrativeListFlow.asStateFlow()

    var pagerDataFlow = SelfData(
        educationLevelShow = "",
        educationLevel = "",
        marryStatusShow = "",
        marryStatus = "",
        childrenTotalShow = "",
        childrenTotal = "",
        familyProvince = "",
        familyCity = "",
        familyAddress = "",
    )


    private val _pagerEventFlow = MutableSharedFlow<PagerEvent>(1)
    val pagerEventFlow = _pagerEventFlow

    init {
        getPageData()
    }

    private fun getPageData() {
        launchUIWithDialog {
            coroutineScope {
                val a = async {
                    _optionShowListFlow.emit(repository.getOptionShowList())
                }
                val b = async {
                    _administrativeListFlow.emit(repository.getAdministrativeList())
                }
                joinAll(a, b)
                if (LocalCache.infoCredit == 1) {
                    pagerDataFlow = repository.getSubmitInfo()
                    pagerEventFlow.emit(PagerEvent.UpdatePageView)
                }
            }
        }
    }

    fun submitInfo() {
        launchUIWithDialog {
            val boolean = repository.submitInfo(pagerDataFlow)
            if (boolean) {
                if (LocalCache.workCredit == 1) {
                    pagerEventFlow.emit(PagerEvent.Finsh)
                } else {
                    pagerEventFlow.emit(PagerEvent.GoWorkPage)
                }
            }
        }
    }
}
