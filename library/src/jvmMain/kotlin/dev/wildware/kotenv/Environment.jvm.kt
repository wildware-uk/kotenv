package dev.wildware.kotenv

actual fun getEnvOrNull(name: String): String? = System.getenv(name)
