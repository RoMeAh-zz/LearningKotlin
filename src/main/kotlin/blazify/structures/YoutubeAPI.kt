package blazify.structures

import blazify.Config
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchResult
import java.net.MalformedURLException
import java.net.URL

class YoutubeAPI {
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

    fun isUrl(input: String): Boolean {
        return try {
            URL(input)
            true
        } catch (ignored: MalformedURLException) {
            false
        }
    }

    fun searchYoutube(input: String): List<SearchResult>? {
        try {
            val results: List<SearchResult> = youTube?.search()
                    ?.list("id,snippet")
                    ?.setQ(input)
                    ?.setMaxResults(10L)
                    ?.setType("video")
                    ?.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                    ?.setKey(Config.ytApiKey)
                    ?.execute()
                    ?.items!!
            if (results.isNotEmpty()) {
                return results
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}