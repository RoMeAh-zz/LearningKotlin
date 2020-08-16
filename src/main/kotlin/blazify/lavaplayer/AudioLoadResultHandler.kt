package blazify.lavaplayer

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import net.dv8tion.jda.api.entities.TextChannel

class AudioLoadResultHandler(var musicManager: GuildMusicManager, var channel: TextChannel): AudioLoadResultHandler {
    override fun loadFailed(exception: FriendlyException?) {
        channel.sendMessage("Whoops something went wrong ${exception}.. Please contact the developers")
    }

    override fun trackLoaded(track: AudioTrack?) {
        musicManager.scheduler.queue(track)
        if (track != null) {
            channel.sendMessage("Adding to queue: `")
                    .append(track.info.title)
                    .append("` by `")
                    .append(track.info.author)
                    .append("`")
                    .queue()
        }
    }

    override fun noMatches() {
        channel.sendMessage("Sorry i couldn't find any track by the given information")
    }

    override fun playlistLoaded(playlist: AudioPlaylist?) {
        if (playlist != null) {
            playlist.tracks.forEach{ musicManager.scheduler.queue(it) }
            channel.sendMessage("Queued a ${playlist.name} with ${playlist.tracks.size}")
        }
    }

}