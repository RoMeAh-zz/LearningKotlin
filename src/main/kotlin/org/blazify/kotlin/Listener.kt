package org.blazify.kotlin

import me.duncte123.botcommons.BotCommons
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.slf4j.LoggerFactory


class Listener : ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        LOGGER.info("{} is ready", event.jda.selfUser.asTag)
    }

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        var prefix: String? = Config["prefix"]
        var raw: String = event.message.contentRaw

        var user: User = event.author

        if(user.isBot || event.isWebhookMessage) {
            return
        }

        if(prefix?.let { raw.startsWith(it) }!! && raw.endsWith("shutdown") && event.author.id == Config["owner_id"]){
            LOGGER.info("Shutting Down Gracefully...")
            event.jda.shutdown()
            BotCommons.shutdown(event.jda)

            return
        }

        if(prefix.let { raw.startsWith(it) }){
            manager.handle(event)
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(Listener::class.java)
        private val manager: CommandHandler = CommandHandler()
    }

}