package KFoot

import com.andreapivetta.kolor.Color
import com.andreapivetta.kolor.Kolor

enum class DEBUG(val value: Int) {
    DEBUG_TEST(3),             // Nos permite seguir el flujo del programa al realizar tests
    DEBUG_AVANZADO(2),         // Imprimirá mucha más información
    DEBUG_SIMPLE(1),           // Imprimirá la información más básica
    DEBUG_NONE(0),             // No queremos debug
}

enum class IMPORTANCIA(val color: Color) {
    ALTA(Color.RED),
    MEDIA(Color.YELLOW),
    BAJA(Color.BLUE),
    NORMAL(Color.BLACK)
}

class Logger {

    companion object {

        private var instance: Logger = Logger()

    }

    private constructor()

    private var DEBUG_LEVEL: DEBUG = DEBUG.DEBUG_NONE

    fun setDebugLevel(debug: DEBUG){
        DEBUG_LEVEL = debug
    }

    /**
     * Logueamos los mensajes pasados por parámetro según el "nivel de debug" necesitado
     * y el establecido para la sesión actual.
     *
     * @param Constantes.DEBUG nivelRequerido: Nivel de debug requerido para mostrar el mensaje
     * @param String mensaje: Texto a mostrar
     * @param Color color: Color con el que se mostrará el mensaje
     */
    fun debug(nivelRequerido: DEBUG, mensaje: String, importancia: IMPORTANCIA = IMPORTANCIA.NORMAL){
        // Comprobamos que queremos loguear
        if (DEBUG_LEVEL.value != DEBUG.DEBUG_NONE.value){

            // El mensaje es de un test y estamos en el nivel de "Test"
            if (nivelRequerido.value == DEBUG_LEVEL.value && DEBUG_LEVEL.value == DEBUG.DEBUG_TEST.value){
                println(Kolor.foreground(mensaje,importancia.color))
            }

            // Ej: Si el nivel actual es 'Avanzado', todos los de nivel 'Simple' también se mostrarán
            else if (nivelRequerido.value <= DEBUG_LEVEL.value && nivelRequerido.value != DEBUG.DEBUG_NONE.value){
                println(Kolor.foreground(mensaje,importancia.color))
            }
        }
    }
}