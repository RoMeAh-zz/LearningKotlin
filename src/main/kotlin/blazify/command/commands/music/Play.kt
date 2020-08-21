package blazify.command.commands.music

import blazify.structures.YoutubeAPI
import blazify.command.BaseCommand
import blazify.command.CommandContext
import blazify.lavaplayer.PlayerManager
import blazify.structures.IMessageCollectorListener
import blazify.structures.MessageCollector
import com.google.api.services.youtube.model.SearchResult


class Play : BaseCommand {
    override fun handle(ctx: CommandContext) {
        if (ctx.args().size == 2) {
            ctx.channel.sendMessage("Please Provide a Music to be Played...").queue()
            return
        }
        if (!ctx.event.member?.voiceState?.inVoiceChannel()!!) {
            ctx.channel.sendMessage("Please Join a Voice Channel in which i should connect...").queue()
            return;
        }
        if (!ctx.selfMember.voiceState?.inVoiceChannel()!!) {
            ctx.channel.sendMessage("I'm not in any Voice Channel... Please use the join command.").queue()
            return;
        }
        if (YoutubeAPI().isUrl(ctx.args()[1])) {
            PlayerManager.instance()?.loadAndPlay(ctx.channel, ctx.args()[1])
        } else {
            val ytSearchResult: List<SearchResult>? = YoutubeAPI().searchYoutube(ctx.args()[1])
            ytSearchResult?.forEach { println(it) }
            val collector = MessageCollector.instance(ctx.channel, ctx.author)
            collector.listener.addListener(Collected(ctx, collector, ytSearchResult))
            collector.start()
        }
    }
        class Collected(private val ctx: CommandContext, private val collector: MessageCollector, private val searchYoutube: List<SearchResult>?) : IMessageCollectorListener {
            override fun onMessageCollected(message: String, map: HashMap<Long, String>) {
                this.proceed(ctx, message, searchYoutube)
                collector.stop()
                return;
            }

            private fun proceed(ctx: CommandContext, message: String, searchYoutube: List<SearchResult>?) {
                if (message == "1") {
                    PlayerManager.instance()?.loadAndPlay(ctx.channel, "https://www.youtube.com/watch?v=${searchYoutube?.get(0)?.id?.videoId}")
                } else if (message == "2") {
                    PlayerManager.instance()?.loadAndPlay(ctx.channel, "https://www.youtube.com/watch?v=${searchYoutube?.get(1)?.id?.videoId}")
                } else if(message == "3") {
                    PlayerManager.instance()?.loadAndPlay(ctx.channel, "https://www.youtube.com/watch?v=${searchYoutube?.get(2)?.id?.videoId}")
                } else if(message == "4") {
                    PlayerManager.instance()?.loadAndPlay(ctx.channel, "https://www.youtube.com/watch?v=${searchYoutube?.get(3)?.id?.videoId}")
                } else if(message == "5") {
                    PlayerManager.instance()?.loadAndPlay(ctx.channel, "https://www.youtube.com/watch?v==${searchYoutube?.get(4)?.id?.videoId}")
                } else {
                    ctx.channel.sendMessage("Sorry i didn't find any expected input from you!")
                    return;
                }
            }
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