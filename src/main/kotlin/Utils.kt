import com.andreapivetta.kolor.Color
import com.andreapivetta.kolor.Kolor
import java.io.File

object Utils {

    /**
     * Comprobamos que sistema operativo tiene el cliente
     *
     * @return Constantes.SO: Sistema operativo del usuario
     */
    fun determinarSistemaOperativo(): Constantes.SO {
        val os = System.getProperty("os.name").toLowerCase()

        when {
            os.indexOf("win") >= 0-> {
                return Constantes.SO.WINDOWS
            }
            os.indexOf("nux") >= 0-> {
                return Constantes.SO.UNIX
            }
        }
        return Constantes.SO.DESCONOCIDO
    }

    /**
     * Comprobamos el idioma que tiene el cliente
     *
     * @return [Constantes.IDIOMA]: Idioma del SO del usuario
     */
    fun determinarIdioma(): Constantes.IDIOMA {

        val idioma = System.getProperty("user.language")

        when {
            idioma.equals("es") -> {return Constantes.IDIOMA.ESPANIOL}
            idioma.equals("en") -> {return Constantes.IDIOMA.INGLES}
        }

        return Constantes.IDIOMA.DESCONOCIDO
    }

    /**
     * Comprobamos si el sistema operativo del cliente
     * es Unix
     *
     * @return Boolean: Si el sistema es UNIX
     */
    fun esUnix(): Boolean{
        return determinarSistemaOperativo() == Constantes.SO.UNIX
    }

    /**
     * Comprobamos si el sistema operativo del cliente
     * es Windows
     *
     * @return Boolean: Si el sistema es Windows
     */
    fun esWindows(): Boolean{
        return determinarSistemaOperativo() == Constantes.SO.WINDOWS
    }

    /**
     * Logueamos los mensajes pasados por parámetro según el "nivel de debug" necesitado
     * y el establecido para la sesión actual.
     *
     * @param Constantes.DEBUG nivelRequerido: Nivel de debug requerido para mostrar el mensaje
     * @param String mensaje: Texto a mostrar
     * @param Color color: Color con el que se mostrará el mensaje
     */
    fun debug(nivelRequerido: Constantes.DEBUG, mensaje: String, color: Color = Color.BLACK){
        // Comprobamos que queremos loguear
        if (Constantes.DEBUG.DEBUG_LEVEL.value != Constantes.DEBUG.DEBUG_NONE.value){

            // El mensaje es de un test y estamos en el nivel de "Test"
            if (nivelRequerido.value == Constantes.DEBUG.DEBUG_LEVEL.value && Constantes.DEBUG.DEBUG_LEVEL.value == Constantes.DEBUG.DEBUG_TEST.value){
                println(Kolor.foreground(mensaje,color))
            }

            // Ej: Si el nivel actual es 'Avanzado', todos los de nivel 'Simple' también se mostrarán
            else if (nivelRequerido.value <= Constantes.DEBUG.DEBUG_LEVEL.value && nivelRequerido.value != Constantes.DEBUG.DEBUG_NONE.value){
                println(Kolor.foreground(mensaje,color))
            }
        }
    }

    /**
     * Retornamos la ruta del directorio 'Documentos' del cliente
     *
     * @return String?: Ruta del directorio
     */
    fun obtenerDirDocumentos(): String?{

        val idioma = determinarIdioma()
        val so = determinarSistemaOperativo()
        val directorioPersonal = obtenerDirPersonal()

        var ruta: String? = null

        when{

            // Ubuntu
            so == Constantes.SO.UNIX -> {
                when {

                    // Español
                    idioma == Constantes.IDIOMA.ESPANIOL -> { ruta = directorioPersonal + "/Documentos"}

                    // Por defecto inglés
                    else -> ruta = directorioPersonal + "/Documents"
                }
            }

            // Windows
            so == Constantes.SO.WINDOWS -> {

                // Tanto en español como en ingles la ruta es "\Documents"
                //ruta = Constantes.DIRECTORIO_PERSONAL + "\\Documents"
                // TODO: Hay problemas a la hora de utilizar "\"
                ruta = directorioPersonal + "/Documents"
            }
        }

        // Comprobamos si la ruta es un directorio válido
        val dirDocumentos = File(ruta)
        if (!dirDocumentos.exists() || !dirDocumentos.isDirectory){
            ruta = null
        }

        return ruta
    }

    /**
     * Retornamos la ruta del directorio personal del cliente
     *
     * @return String: Ruta del directorio
     */
    fun obtenerDirPersonal(): String{
        return System.getProperty("user.home").replace("\\","/")
    }
}