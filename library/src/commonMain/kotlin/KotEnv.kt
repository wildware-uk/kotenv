package dev.wildware.kotenv

import okio.*
import okio.Path.Companion.toPath
import kotlin.reflect.KProperty

private val EXPAND_REGEX = """\$(\w+)""".toRegex()

internal val environment = mutableMapOf<String, String>()

internal var environmentSource: (String) -> String? = ::getEnvOrNull

val envFile: Path by lazy { ".env".toPath() }

private val fileSystem = FileSystem.SYSTEM

fun Path.readFileContents(): String? {
    if(fileSystem.exists(this).not()) return null

    fileSystem.source(this).use { source: Source ->
        return source.buffer().readUtf8()
    }
}

/**
 * Loads and parses the .env file to populate environment variables into the application context.
 *
 * Note: This function should be called at the application startup to ensure all environment
 * variables are set before any other operation depends on them.
 */
fun useDotEnv() {
    parseDotEnv()
}

internal fun parseDotEnv(
    content: String? = null
) {
    if (environment.isNotEmpty()) return

    val content = content ?: envFile.readFileContents() ?: return

    environment.putAll(content.lines()
        .filter { !it.startsWith("#") && it.isNotBlank() }
        .onEach { println(it) }
        .associate { it.split("=").let { it[0] to it[1] } })
}

fun envOrNull(key: String): String? {
    parseDotEnv()

    val envVar = environment[key]
        ?: environmentSource(key)
        ?: return null

    return EXPAND_REGEX.replace(envVar) {
        env(it.groups[1]!!.value)
    }
}

fun env(key: String) = envOrNull(key)
    ?: error("No environment variable: '$key'")


fun <T> environment(
    fallback: () -> String? = { null },
    process: (String) -> T
) = EnvironmentImpl(fallback, process)

fun environment(
    fallback: () -> String? = { null },
) = environment(fallback) { it }

class EnvironmentImpl<T>(
    val fallback: () -> String? = { null },
    val process: (String) -> T
) {
    operator fun getValue(config: Any, property: KProperty<*>): T {
        return process(
            envOrNull(property.name)
                ?: fallback()
                ?: error("No environment variable: '${property.name}'")
        )
    }
}
