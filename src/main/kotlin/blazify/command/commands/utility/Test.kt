package blazify.command.commands.utility

import blazify.command.BaseCommand
import blazify.command.CommandContext
import blazify.structures.IMessageCollectorListener
import blazify.structures.MessageCollector
import blazify.structures.MessageCollectorListener

class Test: BaseCommand {
    lateinit var collector: MessageCollector
    var messageCollected: IMessageCollectorListener = Collected()


    override fun handle(ctx: CommandContext) {
        collector = MessageCollector.instance(ctx.channel, ctx.author)
        collector.listener.addListener(messageCollected)
        collector.start()
    }

    override fun name(): String {
        return "test"
    }

    override fun aliases(): Array<String> {
        return arrayOf("t")
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

class Collected: IMessageCollectorListener {
    override fun onMessageCollected(map: HashMap<Long, String>) {
        map.forEach { (t, u)-> println("Message ID: $t\n, Message Content: $u") }
    }
}