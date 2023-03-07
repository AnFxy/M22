package com.nonetxmxy.mmzqfxy.model

sealed class AuthPagerEvent {
    object UpdatePageView : AuthPagerEvent()
    object GoNextPage : AuthPagerEvent()
    object Finish : AuthPagerEvent()
}