package blazify.structures

import blazify.Bot
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*
import kotlin.collections.HashMap
import kotlin.Long as Long


class MessageCollector {
    lateinit var channel: TextChannel
    lateinit var user: User
    lateinit var EVENTINSTANCE: MessageCollectionEvent
    var listener: MessageCollectorListener = MessageCollectorListener()

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
                init.EVENTINSTANCE = MessageCollectionEvent(init.channel, init.user)
                this.INSTANCE = init
            }
            return this.INSTANCE!!
        }
    }
}

class MessageCollectionEvent(private val channel: TextChannel, private val user: User): ListenerAdapter() {
    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        if(event.channel == channel && event.author == user) {
            MessageCollector.collected[event.messageIdLong] = event.message.contentRaw
            MessageCollector().listener.addMessage(MessageCollector.collected)
        }
    }
}

interface IMessageCollectorListener {
    fun onMessageCollected(map: HashMap<Long, String>);
}

class MessageCollectorListener {
    private val listeners: MutableList<IMessageCollectorListener> = ArrayList()
    fun addListener(listener: IMessageCollectorListener) {
        listeners.add(listener)
    }

    fun addMessage(map: HashMap<Long, String>) {
        for (it in listeners) it.onMessageCollected(map)
    }
}