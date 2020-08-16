package blazify.lavaplayer

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.TextChannel

class PlayerManager {
    private var INSTANCE: PlayerManager? = null;
    private var musicManagers: MutableMap<Long, GuildMusicManager> = HashMap()
    private var audioPlayerManager: AudioPlayerManager = DefaultAudioPlayerManager()

    init {
        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager)
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager)
    }

    private fun musicManager(guild: Guild): GuildMusicManager {
        return musicManagers.computeIfAbsent(guild.idLong) { guildId: Long? ->
            val guildMusicManager = GuildMusicManager(audioPlayerManager)
            guild.audioManager.sendingHandler = guildMusicManager.sendHandler
            guildMusicManager
        }
    }

    fun loadAndPlay(channel: TextChannel, trackUrl: String) {
        val musicManager: GuildMusicManager = this.musicManager(channel.guild)
        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, AudioLoadResultHandler(musicManager, channel))
    }

    fun instance(): PlayerManager? {
        if(INSTANCE == null) {
            INSTANCE = PlayerManager()
        }
        return INSTANCE;
    }
}
