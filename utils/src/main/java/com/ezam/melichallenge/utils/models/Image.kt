package com.ezam.yaperecipies.presentation.model

sealed interface Image {
    data class DrawableRes(val id: Int) : Image
    data class URL(val url: String) : Image
}