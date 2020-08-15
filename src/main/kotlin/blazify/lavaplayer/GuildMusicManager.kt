package blazify.lavaplayer

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager

class GuildMusicManager(manager: AudioPlayerManager) {
    var player: AudioPlayer = manager.createPlayer()
    var scheduler: TrackScheduler = TrackScheduler(this.player)
    var sendHandler: AudioPlayerSendHandler= AudioPlayerSendHandler(this.player)

    init {
        this.player.addListener(this.scheduler)
    }
}