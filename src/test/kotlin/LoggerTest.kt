import KFoot.DEBUG
import KFoot.Logger
import org.junit.Test
import java.io.*


class LoggerTest {

    @Test
    fun no_muestra_log_cuando_no_hay_debug_establecido(){

        // Creamos el stream que recibirá la salida de consola
        val baos = ByteArrayOutputStream()
        val ps = PrintStream(baos)

        // Guardamos la anterior salida
        val old = System.out

        // Establecemos el nuevo stream de salida
        System.setOut(ps)

        Logger.getLogger().debug(DEBUG.DEBUG_SIMPLE,"Mensaje de prueba")
        Logger.getLogger().debug(DEBUG.DEBUG_SIMPLE,"Mensaje de prueba dos")

        // Put things back
        System.out.flush()
        System.setOut(old)

        assert(baos.toString().length == 0)
    }
}