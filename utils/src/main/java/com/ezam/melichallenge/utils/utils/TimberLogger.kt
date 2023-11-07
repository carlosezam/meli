package com.ezam.melichallenge.utils.utils

import android.os.Looper
import android.util.Log
import timber.log.Timber
import javax.inject.Inject

/**
 * Clase que implementa un logger utilizando la biblioteca Timber para gestionar registros (logs) en la aplicación.
 *
 * Esta clase implementa la interfaz [Logger], que define métodos para registrar mensajes de diferentes niveles de gravedad.
 */
class TimberLogger @Inject constructor() : Logger {

    /**
     * Registra un mensaje con nivel de gravedad "VERBOSE".
     *
     * @param message El mensaje a registrar.
     */
    override fun verbose(message: String) {
        Timber.log(Log.VERBOSE, message)
    }

    /**
     * Registra un mensaje con nivel de gravedad "DEBUG".
     *
     * @param message El mensaje a registrar.
     */
    override fun debug(message: String) {
        Timber.log(Log.DEBUG, message)
    }

    /**
     * Registra un mensaje con nivel de gravedad "INFO".
     *
     * @param message El mensaje a registrar.
     */
    override fun info(message: String) {
        Timber.log(Log.INFO, message)
    }

    /**
     * Registra un mensaje con nivel de gravedad "WARN".
     *
     * @param message El mensaje a registrar.
     */
    override fun warn(message: String) {
        Timber.log(Log.WARN, message)
    }

    /**
     * Registra un mensaje con nivel de gravedad "ERROR".
     *
     * @param message El mensaje a registrar.
     */
    override fun error(message: String) {
        Timber.log(Log.ERROR, message)
    }

    /**
     * Registra un mensaje con nivel de gravedad "ASSERT".
     *
     * @param message El mensaje a registrar.
     */
    override fun assert(message: String) {
        Timber.log(Log.ASSERT, message)
    }

}