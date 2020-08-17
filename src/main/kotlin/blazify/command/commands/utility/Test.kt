package blazify.command.commands.utility

import blazify.command.BaseCommand
import blazify.command.CommandContext
import blazify.structures.MessageCollector
import net.dv8tion.jda.api.entities.Guild
import java.util.*

class Test: BaseCommand {
    override fun handle(ctx: CommandContext) {
        ctx.channel.sendMessage("Test Starts").queue()
        MessageCollector().newInstance(ctx.channel, ctx.author).start()
    }

    override fun name(): String {
        return "test"
    }

    override fun aliases(): Array<String> {
        TODO("Not yet implemented")
    }

    override fun description(): String {
        TODO("Not yet implemented")
    }

    override fun usage(): Array<String> {
        TODO("Not yet implemented")
    }

    override fun args(): Array<String>? {
        TODO("Not yet implemented")
    }
}