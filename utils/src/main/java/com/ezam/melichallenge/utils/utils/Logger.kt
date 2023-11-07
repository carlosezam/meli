package com.ezam.melichallenge.utils.utils

/**
 * Interfaz que define los metodos necesarios para implementar un Logger
 */
interface Logger {

    fun verbose(message: String)

    fun debug(message: String)

    fun info(message: String)

    fun warn(message: String)

    fun error(message: String)

    fun assert(message: String)

}