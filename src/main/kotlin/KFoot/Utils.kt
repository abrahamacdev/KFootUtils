package KFoot

import com.andreapivetta.kolor.Color
import com.andreapivetta.kolor.Kolor
import java.io.File
import java.lang.management.ManagementFactory
import java.lang.reflect.Method

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

    /**
     * Comprobamos si existe un archivo a partir de la ruta
     * pasada por parámetro
     *
     * @param ruta: Ruta del archivo a comprobar
     *
     * @return Boolean: Si existe el archivo
     */
    fun existeArchivo(ruta: String): Boolean{
        val f = File(ruta)

        return f.exists() && f.isFile
    }

    /**
     * Comprobamos si existe un directorio a partir de la ruta
     * pasada por parámetro
     *
     * @param ruta: Ruta del archivo a comprobar
     *
     * @return Boolean: Si existe el archivo
     */
    fun existeDirectorio(ruta: String): Boolean{
        val f = File(ruta)

        return f.exists() && f.isDirectory
    }

    /**
     * Retornamos la cantidad de memoria total utilizable
     * para el programa
     *
     * @param unidadAlmacenamiento: Unidad de almacenamiento en la que volveremos el valor
     *
     * @return Long: Cantidad de memoria total disponible en su respectiva unidad
     */
    fun memoriaTotalDisponible(unidadAlmacenamiento: Constantes.UNIDAD_ALMACENAMIENTO = Constantes.UNIDAD_ALMACENAMIENTO.MEGABYTE): Long {

        val runtime = Runtime.getRuntime()
        val base = runtime.maxMemory() - (runtime.totalMemory() - runtime.freeMemory())

        when {
            unidadAlmacenamiento == Constantes.UNIDAD_ALMACENAMIENTO.BYTE -> {
                return base
            }
            unidadAlmacenamiento == Constantes.UNIDAD_ALMACENAMIENTO.KILOBYTE -> {
                return base / 1024
            }
            unidadAlmacenamiento == Constantes.UNIDAD_ALMACENAMIENTO.MEGABYTE -> {
                return base / 1024 / 1024
            }
        }
        return -1
    }

    /**
     * Retornamos la cantidad de memoria total usada
     * por el programa
     *
     * @param unidadAlmacenamiento: Unidad de almacenamiento en la que volveremos el valor
     *
     * @return Long: Cantidad de memoria usada por el programa en su respectiva unidad
     */
    fun memoriaUsada (unidadAlmacenamiento: Constantes.UNIDAD_ALMACENAMIENTO = Constantes.UNIDAD_ALMACENAMIENTO.MEGABYTE): Long {

        when {
            unidadAlmacenamiento == Constantes.UNIDAD_ALMACENAMIENTO.BYTE -> {
                return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
            }
            unidadAlmacenamiento == Constantes.UNIDAD_ALMACENAMIENTO.KILOBYTE -> {
                return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024
            }
            unidadAlmacenamiento == Constantes.UNIDAD_ALMACENAMIENTO.MEGABYTE -> {
                return ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) / 1024
            }
        }
        return -1
    }

    /**
     * Obtenemos el número de hilos totales de la CPU en la que se está
     * ejecutando el programa
     *
     * @return Int: Cantidad de hilos totales
     */
    fun obtenerNumHilos(): Int{
        return ManagementFactory.getThreadMXBean().threadCount
    }

    /**
     * Comprobamos que los métodos recibidos por parámetros
     * sean iguales comprobando entre otras cosas:
     *  - Nombre de los métodos
     *  - Parámetros que reciben cada uno
     *  - Tipos de retorno
     *
     *  @param primero: Primer metodo
     *  @param segundo: Segundo metodo
     *
     *  @return Boolean: Si ambos son el mismo metodo
     */
    fun sonElMismoMetodo(primero: Method, segundo: Method): Boolean{

        // Parámetros de ambos métodos
        val paramsUno = primero.parameters
        val paramsDos = segundo.parameters


        val mismoRetorno = primero.returnType == segundo.returnType
        val mismoNombre = primero.name.equals(segundo.name)
        var mismosParametros = false

        // Comprobamos que tengan el mismo número de parámetros
        if (paramsDos.size == paramsUno.size){

            // No tienen parámetros
            if (paramsUno.size == 0){
                mismosParametros = true
            }

            // Comprobamos que todos los parámetros del primer
            // métodos sean iguales que los del segundo
            else {

                // Recorremos los parametros y comprobamos que tengan el mismo nombre
                // y el mismo tipo
                val todosIguales = paramsUno.firstOrNull{param1 ->
                    paramsDos.find{ param2 ->
                        param1.name.equals(param2.name) && param1.parameterizedType == param2.parameterizedType
                    } != null
                }

                if (todosIguales != null){
                    mismosParametros = true
                }
            }
        }

        return mismoNombre && mismoRetorno && mismosParametros
    }
}