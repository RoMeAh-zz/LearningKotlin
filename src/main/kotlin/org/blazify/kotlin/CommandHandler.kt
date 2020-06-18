package org.blazify.kotlin

import org.blazify.kotlin.Command.Commands.Ping
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.blazify.kotlin.Command.BaseCommand
import org.blazify.kotlin.Command.CommandContext
import org.blazify.kotlin.Config.get
import java.util.*
import java.util.regex.Pattern


class CommandHandler {
    private val commands: MutableList<BaseCommand> = ArrayList()
    private fun addCommand(cmd: BaseCommand) {
        val nameFound = commands.stream().anyMatch { it.name() == (cmd.name()) }
        require(!nameFound) { "A command with this name is already present" }
        commands.add(cmd)
    }

    private fun getCommand(search: String): BaseCommand? {
        val searchLower = search.toLowerCase()
        for (cmd in commands) {
            if (cmd.name() == searchLower || cmd.aliases().contains(searchLower)) {
                return cmd
            }
        }
        return null
    }

    fun handle(event: GuildMessageReceivedEvent) {
        val split = event.message.contentRaw
                .replaceFirst("(?i)" + Pattern.quote(get("prefix")).toRegex(), "")
                .split("\\s+").toTypedArray()
        val invoke = split[0].toLowerCase()
        val cmd = getCommand(invoke)
        if (cmd != null) {
            event.channel.sendTyping().queue()
            val args: List<String> = listOf(*split).subList(1, split.size)
            val ctx = CommandContext(event, args)
            cmd.handle(ctx)
        }
    }

    init {
        addCommand(Ping())
    }
}

