package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.AdministrativeData
import com.nonetxmxy.mmzqfxy.model.AuthPagerEvent
import com.nonetxmxy.mmzqfxy.model.OptionShowList
import com.nonetxmxy.mmzqfxy.model.WorkData
import com.nonetxmxy.mmzqfxy.repository.IUserWorkAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.joinAll
import javax.inject.Inject

@HiltViewModel
class AuthUserWorkViewModel @Inject constructor(private val repository: IUserWorkAuthRepository) :
    BaseViewModel() {
    private val _optionShowListFlow = MutableStateFlow<OptionShowList?>(null)
    val optionShowListFlow = _optionShowListFlow.asStateFlow()

    private val _administrativeListFlow = MutableStateFlow<AdministrativeData?>(null)
    val administrativeListFlow = _administrativeListFlow.asStateFlow()

    private val _pagerEventFlow = MutableSharedFlow<AuthPagerEvent>(1)
    val pagerEventFlow = _pagerEventFlow

    var pagerDataFlow = WorkData(
        workNature = "",
        workNatureShow = "",
        incomeSourceType = "",
        incomeSourceTypeShow = "",
        companyMonthIncome = "",
        companyMonthIncomeShow = ""
    )

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
                if (LocalCache.workCredit == 1) {
                    pagerDataFlow = repository.getSubmitWorkInfo()
                    pagerEventFlow.emit(AuthPagerEvent.UpdatePageView)
                }
            }
        }
    }

    fun submitWorkInfo() {
        launchUIWithDialog {
            val boolean = repository.submitWorkInfo(pagerDataFlow)
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