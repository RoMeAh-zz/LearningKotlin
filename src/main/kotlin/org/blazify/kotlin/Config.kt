package org.blazify.kotlin

import io.github.cdimascio.dotenv.Dotenv


object Config {
    private val dotenv: Dotenv = Dotenv.load()
    operator fun get(key: String): String? {
        return dotenv[key.toUpperCase()]
    }
}


