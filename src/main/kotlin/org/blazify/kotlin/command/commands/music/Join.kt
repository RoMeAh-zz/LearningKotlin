package org.blazify.kotlin.command.commands.music

import lavalink.client.io.jda.JdaLink
import org.blazify.kotlin.Bot
import org.blazify.kotlin.command.BaseCommand
import org.blazify.kotlin.command.CommandContext

class Join: BaseCommand {
    override fun handle(ctx: CommandContext) {
        if (!ctx.event.member?.voiceState?.inVoiceChannel()!!) {
            ctx.channel.sendMessage("Please Join a Voice Channel in which i should connect...").queue()
            return;
        }
        var link: JdaLink? = Bot().lavalink?.getLink(ctx.guild)
        link?.connect(ctx.event.member!!.voiceState?.channel)
    }

    override fun name(): String {
        return "join"
    }

    override fun aliases(): Array<String> {
        return arrayOf("joinvc")
    }

    override fun description(): String {
        return "Joins a Voice Channel for the music to be played"
    }

    override fun usage(): Array<String> {
        return arrayOf("ktjoin")
    }

    override fun args(): Array<String>? {
        return null
    }
}

