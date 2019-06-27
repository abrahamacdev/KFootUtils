package KFoot

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

    val PRIMITIVOS = arrayListOf<String>(Boolean.javaClass.canonicalName,Byte.javaClass.canonicalName,Short.javaClass.canonicalName,Int.javaClass.canonicalName,Long.javaClass.canonicalName,Float.javaClass.canonicalName,Double.javaClass.canonicalName,Char.javaClass.canonicalName)

}
