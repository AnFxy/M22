package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.model.OptionShowList
import com.nonetxmxy.mmzqfxy.model.SelfData
import com.nonetxmxy.mmzqfxy.repository.IUserAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthUserIndoViewModel @Inject constructor(private val repository: IUserAuthRepository) :
    BaseViewModel() {

    private val _optionShowListFlow = MutableStateFlow<OptionShowList?>(null)
    val optionShowListFlow = _optionShowListFlow.asStateFlow()

    private val _pagerDataFlow = MutableStateFlow(
        SelfData(
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
    )
    val pagerDataFlow = _pagerDataFlow.asStateFlow()

    init {
        getPageData()
    }

    private fun getPageData() {
        launchUIWithDialog {
            _optionShowListFlow.emit(repository.getOptionShowList())
        }
    }
}
