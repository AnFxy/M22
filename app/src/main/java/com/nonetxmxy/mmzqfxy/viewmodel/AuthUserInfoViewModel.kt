package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.AdministrativeData
import com.nonetxmxy.mmzqfxy.model.AuthPagerEvent
import com.nonetxmxy.mmzqfxy.model.OptionShowList
import com.nonetxmxy.mmzqfxy.model.SelfData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.joinAll
import javax.inject.Inject

@HiltViewModel
class AuthUserInfoViewModel @Inject constructor(private val repository: IUserInfoAuthRepository) :
    BaseViewModel() {

    private val _optionShowListFlow = MutableStateFlow<OptionShowList?>(null)
    val optionShowListFlow = _optionShowListFlow.asStateFlow()

    private val _administrativeListFlow = MutableStateFlow<AdministrativeData?>(null)
    val administrativeListFlow = _administrativeListFlow.asStateFlow()

    var pagerData = SelfData(
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

    private val _pagerEventFlow = MutableSharedFlow<AuthPagerEvent>()
    val pagerEventFlow = _pagerEventFlow

    init {
        getPageData()
    }

    private fun getPageData() {
        launchUIWithDialog {
            coroutineScope {
                joinAll(async {
                    _optionShowListFlow.emit(repository.getOptionShowList())
                }, async {
                    _administrativeListFlow.emit(repository.getAdministrativeList())
                })
                if (LocalCache.infoCredit == 1) {
                    pagerData = repository.getSubmitInfo()
                    pagerEventFlow.emit(AuthPagerEvent.UpdatePageView)
                }
            }
        }
    }

    fun submitInfo() {
        launchUIWithDialog {
            val boolean = repository.submitInfo(pagerData)
            if (boolean) {
                if (LocalCache.workCredit == 1) {
                    pagerEventFlow.emit(AuthPagerEvent.Finish)
                } else {
                    pagerEventFlow.emit(AuthPagerEvent.GoNextPage)
                }
            }
        }
    }
}
