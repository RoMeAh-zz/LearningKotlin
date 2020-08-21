package blazify

import blazify.command.commands.music.Join
import blazify.command.commands.music.Leave
import blazify.command.commands.music.Play
import blazify.command.commands.utility.Ping
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.slf4j.LoggerFactory

class Listener : ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        LOGGER.info("{} is ready", event.jda.selfUser.asTag)
        manager.addCommand(Ping(), Join(), Play(), Leave())
        LOGGER.info("Commands Loaded!")
    }

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        val prefix: String = Config.prefix
        val raw: String = event.message.contentRaw
        val user: User = event.author

        if(user.isBot || event.isWebhookMessage) {
            return
        }
        if(raw.startsWith(prefix, true)) manager.handle(event)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(Listener::class.java)
        private val manager: CommandHandler = CommandHandler()
    }

}
