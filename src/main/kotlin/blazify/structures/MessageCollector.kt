package blazify.structures

import blazify.Bot
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageCollector {
    var INSTANCE: MessageCollector? = null
    lateinit var channel: TextChannel
    lateinit var user: User
    fun start() {
        Bot.sharder.addEventListener(MessageCollectorEvent(channel, user))
    }
    fun stop(): HashMap<Long, String> {
        Bot.sharder.removeEventListener(MessageCollectorEvent(channel, user))
        collected.forEach { t, u -> println("MessageID: $t, Message Content: $u") }
        INSTANCE = null
        return collected
    }
    fun newInstance(channel: TextChannel, user: User): MessageCollector {
        if(this.INSTANCE == null) {
            val init = MessageCollector()
            init.user = user
            init.channel = channel
            this.INSTANCE = init
        }
        return this.INSTANCE!!
    }

    companion object {
        public var collected: HashMap<Long, String> = HashMap()
    }
}

class MessageCollectorEvent(private val channel: TextChannel, private val user: User): ListenerAdapter() {
    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if(event.channel == channel && event.author == user) {
            MessageCollector.collected[event.messageIdLong] = event.message.contentRaw
            println("Collected")
            if(event.message.contentRaw == "stop")
           MessageCollector().newInstance(channel, user).stop()
        }
    }
}