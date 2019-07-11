import KFoot.Utils
import org.junit.Test
import java.lang.Exception

class UtilsTest {

    @Test
    fun se_comprueba_que_el_archivo_existe(){

        val f = "C:/Users/abrah/Desktop/Prueba.txt"

        assert(!Utils.existeArchivo(f))
    }

    @Test
    fun se_comprueba_que_ambos_metodos_son_iguales(){

        val c = Utils::class.java.declaredMethods.find { it.name.equals("esSubclase") }

        val r = Utils.sonElMismoMetodo(c!!,c)

        assert(r == true)
    }
}
