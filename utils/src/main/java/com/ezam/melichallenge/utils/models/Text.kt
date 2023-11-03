package com.ezam.yaperecipies.presentation.model

sealed interface Text {
    data class StringRes(val id: Int, val params: Array<Any> = emptyArray()) : Text
    data class StringValue(val value: String) : Text
}