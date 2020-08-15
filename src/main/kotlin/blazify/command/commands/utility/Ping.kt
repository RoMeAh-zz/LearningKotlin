package blazify.command.commands.utility

import blazify.command.CommandContext
import net.dv8tion.jda.api.JDA
import blazify.command.BaseCommand

 class Ping : BaseCommand {
    override fun handle(ctx: CommandContext) {
        val jda: JDA = ctx.jda
        jda.restPing.queue { ping: Long? ->
            ctx.channel
                    .sendMessage("Reset ping: ${ping}ms\nWS ping: ${jda.gatewayPing}ms").queue()
        }
    }

    override fun name(): String {
        return "ping"
    }

    override fun aliases(): Array<String> {
        return arrayOf("pong", "pingpong")
    }

     override fun description(): String {
        return "Shows the API Latency"
     }

     override fun usage(): Array<String> {
         return arrayOf("ktping")
     }

     override fun args(): Array<String>? {
        return null;
     }
 }