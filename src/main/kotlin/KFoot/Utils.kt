package KFoot

import com.andreapivetta.kolor.Color
import com.andreapivetta.kolor.Kolor
import java.io.File

object Utils {

    /**
     * Comprobamos que sistema operativo tiene el cliente
     *
     * @return KFoot.Constantes.SO: Sistema operativo del usuario
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
            idioma.equals("es") -> {return Constantes.IDIOMA.ESPANIOL
            }
            idioma.equals("en") -> {return Constantes.IDIOMA.INGLES
            }
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
                //ruta = KFoot.Constantes.DIRECTORIO_PERSONAL + "\\Documents"
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

    /**
     * Comprobamos si el objeto recibido por parámetro es
     * de algún tipo primitivo
     *
     * @param objeto: Objeto a comprobar
     *
     * @return Boolean: Si el objeto es de algún tipo primitivo
     */
    fun esPrimitivo(objeto: Any): Boolean{
        val clase = objeto.javaClass.canonicalName

        return clase in Constantes.PRIMITIVOS
    }

    /**
     * Comprobamos que la clase del nuevo item sea una
     * subclase del que se usa actualmente en el repositorio
     *
     * @param claseHija: Clase que supuestamente es hija
     * @param clasePadre: Clase que supuestamente es padre
     *
     * @return Boolean: Si efectivamente la clase hija hereda de la clase padre
     */
    fun esSubclase(claseHija: Class<*>, clasePadre: Class<*>): Boolean {

        var superClase: Class<*>? = claseHija.superclass

        while (superClase != null){

            if (superClase == clasePadre){
                return true
            }
        }
        return false
    }

}