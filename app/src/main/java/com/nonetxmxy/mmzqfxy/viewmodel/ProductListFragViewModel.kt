package com.nonetxmxy.mmzqfxy.viewmodel

import com.nonetxmxy.mmzqfxy.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class ProductListFragViewModel @Inject constructor() : BaseViewModel() {

    init {
        launchUIWithDialog {
            delay(1000)
        }
    }
}
