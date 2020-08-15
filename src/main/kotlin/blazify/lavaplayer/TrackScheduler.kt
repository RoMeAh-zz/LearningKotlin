package blazify.lavaplayer

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue





class TrackScheduler(player: AudioPlayer?): AudioEventAdapter() {
    private final var player: AudioPlayer = player!!;
    private final var queue: BlockingQueue<AudioTrack> = LinkedBlockingQueue()

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
        return print("Player: ${player}\nTrack: ${track}\nException: ${exception} ")
    }

    override fun onTrackStart(player: AudioPlayer?, track: AudioTrack?) {
        super.onTrackStart(player, track)
    }

    override fun onTrackStuck(player: AudioPlayer?, track: AudioTrack?, thresholdMs: Long) {
        super.onTrackStuck(player, track, thresholdMs)
    }
}