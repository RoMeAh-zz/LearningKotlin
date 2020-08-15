package blazify.command.commands.music

import blazify.command.BaseCommand
import blazify.command.CommandContext

class Leave: BaseCommand {
    override fun handle(ctx: CommandContext) {
        if(ctx.event.member?.voiceState?.inVoiceChannel()!!) {
            ctx.channel.sendMessage("You are not present in any voice channel");
            return;
        }


        ctx.channel.sendMessage("Bye Bye :wave: I left the voice Channel...")
    }

    override fun name(): String {
        return "leave"
    }

    override fun aliases(): Array<String> {
        return arrayOf("bye")
    }

    override fun description(): String {
        return "Leaves a Voice Channel"
    }

    override fun usage(): Array<String> {
        return arrayOf("ktleave")
    }

    override fun args(): Array<String>? {
        return null
    }
}