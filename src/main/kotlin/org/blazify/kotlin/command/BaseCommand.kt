package org.blazify.kotlin.command

interface BaseCommand {
    fun handle(ctx: CommandContext)
    fun name(): String
    fun aliases(): Array<String>
    fun description(): String
    fun usage(): Array<String>
    fun args(): Array<String>?
}

