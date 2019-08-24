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
    BAJA(Color.GREEN),
    NORMAL(Color.BLACK)
}

class Logger {

    companion object {

        private val instance: Logger = Logger()

        fun getLogger(): Logger{
            return instance
        }

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

        val claseMetodoLlamada = obtenerClaseMetodoLlamada()
        var tracker = ""

        if (claseMetodoLlamada != null){
            tracker = "["
            tracker += Kolor.foreground("Clase: ${claseMetodoLlamada.first}", Color.BLUE)
            tracker += " - "
            tracker += Kolor.foreground("Método: ${claseMetodoLlamada.second}", Color.MAGENTA)
            tracker += "] -> "
        }

        // Comprobamos que queremos loguear
        if (DEBUG_LEVEL.value != DEBUG.DEBUG_NONE.value){

            // El mensaje es de un test y estamos en el nivel de "Test"
            if (nivelRequerido.value == DEBUG_LEVEL.value && DEBUG_LEVEL.value == DEBUG.DEBUG_TEST.value){
                println(Kolor.foreground(tracker + mensaje,importancia.color))
            }

            // Ej: Si el nivel actual es 'Avanzado', todos los de nivel 'Simple' también se mostrarán
            else if (nivelRequerido.value <= DEBUG_LEVEL.value && nivelRequerido.value != DEBUG.DEBUG_NONE.value){
                println(Kolor.foreground(tracker + mensaje,importancia.color))
            }
        }
    }

    /**
     * Obtenemos la clase y método desde donde se ha realizado la
     * llamada a [debug]
     *
     * @return Pair<String,String>?: Par con la clase y método de procedencia de la llamada
     */
    private fun obtenerClaseMetodoLlamada(): Pair<String,String>? {

        // Recorremos el stack de llamadas
        for (i in 0 until Thread.currentThread().getStackTrace().size){

            val actual = Thread.currentThread().getStackTrace().get(i)

            // Comprobamos si el elemento actual tiene el mismo nombre que [this.javaclass.name]
            if (actual.className.equals(javaClass.name)){

                // Cogemos el siguiente elemento
                if (i+1 < Thread.currentThread().getStackTrace().size){
                    val siguiente = Thread.currentThread().getStackTrace().get(i+1)

                    // Comprobamos si es el que ha solicitado el log
                    if (!siguiente.className.equals(javaClass.name)){
                        return Pair(siguiente.className,siguiente.methodName)
                    }
                }
            }
        }
        return null
    }
}