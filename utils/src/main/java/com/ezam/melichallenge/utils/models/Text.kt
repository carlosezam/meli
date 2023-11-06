package com.ezam.yaperecipies.presentation.model

sealed interface Text {
    data class StringRes(val id: Int, val params: Array<Any> = emptyArray()) : Text {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as StringRes

            if (id != other.id) return false
            if (!params.contentEquals(other.params)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + params.contentHashCode()
            return result
        }
    }

    data class StringValue(val value: String) : Text
}