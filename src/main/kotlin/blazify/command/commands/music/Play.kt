package blazify.command.commands.music

import blazify.Bot
import blazify.command.BaseCommand
import blazify.command.CommandContext
import lavalink.client.player.IPlayer
import lavalink.client.player.TrackData


class Play: BaseCommand {
    override fun handle(ctx: CommandContext) {
        if(ctx.args()[1].length > 1) return
        val Player: IPlayer = Bot.lavalink.getLink(ctx.guild).player

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