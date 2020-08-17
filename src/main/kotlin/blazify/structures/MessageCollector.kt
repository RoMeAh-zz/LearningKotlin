package blazify.structures

import blazify.Bot
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageCollector {
    lateinit var channel: TextChannel
    lateinit var user: User
    lateinit var EVENTINSTANCE: MessageCollectorEvent
    fun start() {
        Bot.sharder.addEventListener(this.EVENTINSTANCE)
    }
    fun stop(): HashMap<Long, String> {
        Bot.sharder.removeEventListener(this.EVENTINSTANCE)
        collected.forEach { (t, u) -> println("MessageID: $t, Message Content: $u") }
        INSTANCE = null
        return collected
    }

    companion object {
        var collected: HashMap<Long, String> = HashMap()
        private var INSTANCE: MessageCollector? = null

        fun instance(channel: TextChannel, user: User): MessageCollector {
            if(this.INSTANCE == null) {
                val init = MessageCollector()
                init.user = user
                init.channel = channel
                init.EVENTINSTANCE = MessageCollectorEvent(init.channel, init.user)
                this.INSTANCE = init
            }
            return this.INSTANCE!!
        }
    }
}

class MessageCollectorEvent(private val channel: TextChannel, private val user: User): ListenerAdapter() {
    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if(event.channel == channel && event.author == user) {
            MessageCollector.collected[event.messageIdLong] = event.message.contentRaw
            println("Collected")
            if(event.message.contentRaw == "stop")
           MessageCollector.instance(channel, user).stop()
        }
    }
}