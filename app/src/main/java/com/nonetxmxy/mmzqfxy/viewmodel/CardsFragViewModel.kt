package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.model.SampleBank
import com.nonetxmxy.mmzqfxy.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CardsFragViewModel @Inject constructor(
    private val authRepository: IAuthRepository
) : BaseViewModel() {

    val cards = MutableStateFlow<List<SampleBank>>(emptyList())

    init {
        getAllBanks()
    }

    private fun getAllBanks() {
        launchUIWithDialog {
            cards.value = authRepository.getCards()
        }
    }
}
