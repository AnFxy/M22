package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import com.nonetxmxy.mmzqfxy.model.auth.BankMessage
import com.nonetxmxy.mmzqfxy.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CardsFragViewModel @Inject constructor(
    private val authRepository: IAuthRepository
) : BaseViewModel() {

    private val _cards = MutableStateFlow<List<BankMessage>>(emptyList())
    val cards: StateFlow<List<BankMessage>> = _cards

    init {
        getAllBanks()
    }

    fun getAllBanks() {
        launchUIWithDialog {
            _cards.value = authRepository.getSubmitBanks()
        }
    }
}
