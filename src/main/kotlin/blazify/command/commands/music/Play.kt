package blazify.command.commands.music

import blazify.command.BaseCommand
import blazify.command.CommandContext
import blazify.lavaplayer.PlayerManager


class Play: BaseCommand {
    override fun handle(ctx: CommandContext) {
        if (ctx.args()[1].isEmpty()) {
            ctx.channel.sendMessage("Please Provide a Music to be Played...").queue()
            return
        }
        if (!ctx.event.member?.voiceState?.inVoiceChannel()!!) {
            ctx.channel.sendMessage("Please Join a Voice Channel in which i should connect...").queue()
            return;
        }
        if (!ctx.selfMember.voiceState?.inVoiceChannel()!!) {
            ctx.channel.sendMessage("I'm not in any Voice Channel... Please use the join command.").queue()
            return;
        }

        PlayerManager().instance()?.loadAndPlay(ctx.channel, ctx.args()[1])
    }

    override fun name(): String {
        return "play"
    }

    override fun aliases(): Array<String> {
        return arrayOf("p", "pplay")
    }

    override fun description(): String {
        return "Plays a Music in a Voice Channel"
    }

    override fun usage(): Array<String> {
        return arrayOf("ktp Believer", "ktplay https://www.youtube.com/watch?v=tracks")
    }

    override fun args(): Array<String>? {
        return arrayOf("[song name]")
    }

}