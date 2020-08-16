package blazify.command.commands.music

import blazify.command.BaseCommand
import blazify.command.CommandContext
import blazify.lavaplayer.PlayerManager
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchResult
import java.net.MalformedURLException
import java.net.URL


class Play: BaseCommand {
    private var youTube: YouTube? = null
    init {
        var temp: YouTube? = null
        try {
            temp = YouTube.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    null
            )
                    .setApplicationName("Blazify")
                    .build()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        youTube = temp
    }
    override fun handle(ctx: CommandContext) {
        if (ctx.args()[1].isEmpty()) {
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
        if (!isUrl(ctx.args()[1])) {
            val ytSearched = searchYoutube(ctx.args()[1])
            if (ytSearched == null) {
                ctx.channel.sendMessage("Youtube returned no results").queue()
                return
            }
            ctx.args()[1] = ytSearched
        }

        PlayerManager().instance()?.loadAndPlay(ctx.channel, ctx.args()[1])
    }

    private fun isUrl(input: String): Boolean {
        return try {
            URL(input)
            true
        } catch (ignored: MalformedURLException) {
            false
        }
    }

    private fun searchYoutube(input: String): String? {
        try {
            val results: List<SearchResult> = youTube?.search()
                    ?.list("id,snippet")
                    ?.setQ(input)
                    ?.setMaxResults(1L)
                    ?.setType("video")
                    ?.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                    ?.setKey(Config.ytApiKey)
                    ?.execute()
                    ?.items!!
            if (results.isNotEmpty()) {
                val videoId: String = results[0].id.videoId
                return "https://www.youtube.com/watch?v=$videoId"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
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