package blazify

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame
import net.dv8tion.jda.api.audio.AudioSendHandler
import java.nio.Buffer
import java.nio.ByteBuffer

class AudioPlayerSendHandler: AudioSendHandler {
    private final lateinit var audioPlayer: AudioPlayer
    private final lateinit var buffer: ByteBuffer
    private final lateinit var frame: MutableAudioFrame

    fun AudioPlayerSendHandler(audioPlayer: AudioPlayer) {
        this.audioPlayer = audioPlayer
        this.buffer = ByteBuffer.allocate(1024)
        this.frame = MutableAudioFrame()
        this.frame.setBuffer(buffer)
    }
    override fun provide20MsAudio(): ByteBuffer? {
        return this.buffer.flip()
    }

    override fun canProvide(): Boolean {
        return this.audioPlayer.provide(frame)
    }

    override fun isOpus(): Boolean {
        return true
    }
}