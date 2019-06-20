object Constantes {

    enum class SO{
        WINDOWS,
        UNIX,
        DESCONOCIDO
    }

    enum class IDIOMA {
        ESPANIOL,
        INGLES,
        DESCONOCIDO
    }

    enum class EXTENSIONES_ARCHIVOS {
        csv,
        json
    }

    // -- DEBUG --
    enum class DEBUG(val value: Int) {
        DEBUG_TEST(3),             // Nos permite seguir el flujo del programa al realizar tests
        DEBUG_AVANZADO(2),         // Imprimirá mucha más información
        DEBUG_SIMPLE(1),           // Imprimirá la información más básica
        DEBUG_NONE(0),             // No queremos debug
        DEBUG_LEVEL(DEBUG_TEST.value)    // Debug que queremos para la ejecución actual
    }
    // -----------------------------------------------
}
