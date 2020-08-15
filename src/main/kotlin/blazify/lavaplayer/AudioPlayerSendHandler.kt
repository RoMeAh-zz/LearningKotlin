package blazify.lavaplayer

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame
import net.dv8tion.jda.api.audio.AudioSendHandler
import java.nio.ByteBuffer

class AudioPlayerSendHandler(audioPlayer: AudioPlayer): AudioSendHandler {
    private var audioPlayer: AudioPlayer = audioPlayer
    private var buffer: ByteBuffer = ByteBuffer.allocate(1024)
    private var frame: MutableAudioFrame = MutableAudioFrame()

    override fun provide20MsAudio(): ByteBuffer? {
        return this.buffer.flip()
    }

    override fun canProvide(): Boolean {
        this.frame.setBuffer(buffer)
        return this.audioPlayer.provide(frame)
    }

    override fun isOpus(): Boolean {
        return true
    }
}