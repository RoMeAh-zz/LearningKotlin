package org.blazify.kotlin.Command.Commands

import org.blazify.kotlin.Command.CommandContext
import net.dv8tion.jda.api.JDA
import org.blazify.kotlin.Command.BaseCommand

class Ping : BaseCommand {
    override fun handle(ctx: CommandContext) {
        val jda: JDA = ctx.jda
        jda.restPing.queue { ping: Long? ->
            ctx.channel
                    .sendMessageFormat("Reset ping: %sms\nWS ping: %sms", ping, jda.gatewayPing).queue()
        }
    }

    override fun name(): String {
        return "ping"
    }

    override fun aliases(): Array<String> {
        return arrayOf("pong", "pingpong")
    }
}