package dev.wildware.kotenv

import kotlinx.cinterop.*
import platform.windows.*

private val bufferLength = 32767

/**
 * Retrieves the value of the specified environment variable, or null if the environment variable is not found.
 *
 * @param name The name of the environment variable whose value is to be retrieved.
 * @return The value of the environment variable as a String if it exists, null otherwise.
 */
@OptIn(ExperimentalForeignApi::class)
actual fun getEnvOrNull(name: String): String? {
    memScoped {
        val buffer = allocArray<UShortVar>(bufferLength)
        val result = GetEnvironmentVariableW(name, buffer, bufferLength.toUInt())

        return if (result > 0u) buffer.toKString() else null
    }
}