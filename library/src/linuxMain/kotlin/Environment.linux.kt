package dev.wildware.kotenv

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import platform.posix.getenv


/**
 * Retrieves the value of the specified environment variable, or null if the environment variable is not found.
 *
 * @param name The name of the environment variable whose value is to be retrieved.
 * @return The value of the environment variable as a String if it exists, null otherwise.
 */
@OptIn(ExperimentalForeignApi::class)
actual fun getEnvOrNull(name: String): String? {
    return getenv(name)?.toKString()
}
