package org.blazify.kotlin.command

import me.duncte123.botcommons.commands.ICommandContext
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class CommandContext(
        private val event: GuildMessageReceivedEvent,
        private val args: List<String>
) : ICommandContext {
    override fun getGuild(): Guild {
        return getEvent().guild
    }

    override fun getEvent(): GuildMessageReceivedEvent {
        return event
    }

    fun args(): List<String> {
        return args
    }

}