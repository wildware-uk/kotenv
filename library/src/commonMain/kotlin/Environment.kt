package dev.wildware.kotenv

/**
 * Retrieves the value of the specified environment variable.
 *
 * @param name The name of the environment variable whose value is to be retrieved.
 * @return The value of the environment variable as a String.
 *
 * @throws IllegalArgumentException if the environment variable [name] is not found.
 */
internal fun getEnv(name: String): String {
    return getEnvOrNull(name)
        ?: throw IllegalArgumentException("Missing environment variable $name")
}

/**
 * Retrieves the value of the specified environment variable, or null if the environment variable is not found.
 *
 * @param name The name of the environment variable whose value is to be retrieved.
 * @return The value of the environment variable as a String if it exists, null otherwise.
 */
internal expect fun getEnvOrNull(name: String): String?
