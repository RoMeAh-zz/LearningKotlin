package blazify.lavaplayer

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.entities.TextChannel

class AudioLoadResultHandler(var musicManager: GuildMusicManager, var channel: TextChannel): AudioLoadResultHandler {
    override fun loadFailed(exception: FriendlyException?) {
        TODO("Not yet implemented")
    }

    override fun trackLoaded(track: AudioTrack?) {
        musicManager.scheduler.queue(track)
        if (track != null) {
            channel.sendMessage("Adding to queue: `")
                    .append(track.info.title)
                    .append("` by `")
                    .append(track.info.author).queue()
        }
    }

    override fun noMatches() {
        TODO("Not yet implemented")
    }

    override fun playlistLoaded(playlist: AudioPlaylist?) {
        TODO("Not yet implemented")
    }

}