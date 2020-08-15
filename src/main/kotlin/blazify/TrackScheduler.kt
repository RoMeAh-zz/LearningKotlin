package blazify

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue





class TrackScheduler: AudioEventAdapter() {
    private final lateinit var player: AudioPlayer;
    private final lateinit var queue: BlockingQueue<AudioTrack>

    fun TrackScheduler(player: AudioPlayer?) {
        this.player = player!!
        queue = LinkedBlockingQueue()
    }
    fun nextTrack() {
        this.player.startTrack(this.queue.poll(), false)
    }

    fun queue(track: AudioTrack?) {
        if(!this.player.startTrack(track, true)) {
            this.queue.offer(track);
        }
    }

    override fun onEvent(event: AudioEvent?) {
        super.onEvent(event)
    }
    override fun onPlayerPause(player: AudioPlayer?) {
        super.onPlayerPause(player)
    }

    override fun onPlayerResume(player: AudioPlayer?) {
        super.onPlayerResume(player)
    }

    override fun onTrackEnd(player: AudioPlayer?, track: AudioTrack?, endReason: AudioTrackEndReason?) {
        if (endReason != null) {
            if(endReason.mayStartNext) {
                nextTrack()
            }
        }
    }

    override fun onTrackException(player: AudioPlayer?, track: AudioTrack?, exception: FriendlyException?) {
        super.onTrackException(player, track, exception)
    }

    override fun onTrackStart(player: AudioPlayer?, track: AudioTrack?) {
        super.onTrackStart(player, track)
    }

    override fun onTrackStuck(player: AudioPlayer?, track: AudioTrack?, thresholdMs: Long) {
        super.onTrackStuck(player, track, thresholdMs)
    }
}