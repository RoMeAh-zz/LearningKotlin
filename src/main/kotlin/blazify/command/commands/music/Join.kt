package blazify.command.commands.music


import blazify.Bot
import blazify.command.BaseCommand
import blazify.command.CommandContext
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.managers.AudioManager


class Join: BaseCommand {

    override fun handle(ctx: CommandContext) {

        if (!ctx.event.member?.voiceState?.inVoiceChannel()!!) {
            ctx.channel.sendMessage("Please Join a Voice Channel in which i should connect...").queue()
            return;
        }
        if(ctx.selfMember.voiceState?.inVoiceChannel()!!) {
            ctx.channel.sendMessage("I'm already in a Voice Channel").queue()
            return;
        }
        if(!ctx.selfMember.hasPermission(Permission.VOICE_CONNECT)) {
            ctx.channel.sendMessage("I don't have the `VOICE_CONNECT` Permission(s)").queue()
            return;
        }

        val audioManager: AudioManager = ctx.guild.audioManager
        val voiceChannel = ctx.member.voiceState?.channel

        audioManager.openAudioConnection(voiceChannel)

        ctx.channel.sendMessage("Yeet i joined the voice channel!").queue()
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

