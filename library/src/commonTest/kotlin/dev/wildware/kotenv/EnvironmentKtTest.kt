import dev.wildware.kotenv.getEnv
import dev.wildware.kotenv.getEnvOrNull
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class EnvironmentKtTest {

    @Test
    fun getEnv() {
        val path = getEnv("PATH")
        assertNotNull(path)
    }

     @Test
    fun getEnvOrNull() {
        val path = getEnvOrNull("PATH2")
        assertNull(path)
    }
}
