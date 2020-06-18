package org.blazify.kotlin.Command

interface BaseCommand {
    fun handle(ctx: CommandContext)

    fun name(): String

    fun aliases(): Array<String> {
        return aliases()
    }
}