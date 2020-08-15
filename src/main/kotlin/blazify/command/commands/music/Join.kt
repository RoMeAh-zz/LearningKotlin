package blazify.command.commands.music


import blazify.Bot
import blazify.command.BaseCommand
import blazify.command.CommandContext


class Join: BaseCommand {

    override fun handle(ctx: CommandContext) {

        if (!ctx.event.member?.voiceState?.inVoiceChannel()!!) {
            ctx.channel.sendMessage("Please Join a Voice Channel in which i should connect...").queue()
            return;
        }
      Bot.lavalink.getLink(ctx.guild).connect(ctx.event.member!!.voiceState?.channel)
        ctx.channel.sendMessage("Yeet i joined the voice channel!")
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

