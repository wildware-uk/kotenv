package dev.wildware.kotenv

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EnvironmentUtilTest {

    @BeforeTest
    fun setup() {
        environment.clear()
    }

    @Test
    fun `loadDotEnv should load environment variables from env file`() {
        parseDotEnv(
            """
            KEY1=value1
            KEY2=value2
            #KEY3=value3
            KEY4=value4${'$'}KEY1
        """.trimIndent()
        )

        assertEquals("value1", envOrNull("KEY1"))
        assertEquals("value2", envOrNull("KEY2"))
        assertEquals(null, envOrNull("KEY3"))
        assertEquals("value4value1", envOrNull("KEY4"))
    }


    @Test
    fun `envOrNull should return null for non-existent keys`() {
        assertEquals(null, envOrNull("NON_EXISTENT_KEY"))
    }

    @Test
    fun `envOrNull should prioritize dot env file over system environment`() {
        parseDotEnv("PRIORITY_KEY=env_file_value")
        environmentSource = mapOf("PRIORITY_KEY" to "system_env_value")::get

        assertEquals("env_file_value", envOrNull("PRIORITY_KEY"))
    }

    @Test
    fun `envOrNull should fall back to system environment if key not in dot env`() {
        environmentSource = mapOf("SYSTEM_KEY" to "system_value")::get
        assertEquals("system_value", envOrNull("SYSTEM_KEY"))
    }

    @Test
    fun `env should throw error for non-existent keys`() {
        val exception = assertFailsWith<IllegalStateException> {
            env("NON_EXISTENT_KEY")
        }
        assertEquals("No environment variable: 'NON_EXISTENT_KEY'", exception.message)
    }

    @Test
    fun `envOrNull should expand nested environment variables`() {
        parseDotEnv(
            """
        BASE_URL=http://example.com
        API_URL=${'$'}BASE_URL/api
        NESTED_URL=${'$'}API_URL/v1
        """.trimIndent()
        )
        assertEquals("http://example.com/api/v1", envOrNull("NESTED_URL"))
    }
}