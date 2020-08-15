package blazify

import lavalink.client.io.jda.JdaLavalink
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity.playing
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager
import java.net.URI




fun main() {
       Bot().run()
}


class Bot {
       fun run() {
              lavalink = JdaLavalink(
                      "722834420226195517",
                      1
              ) { sharder.getShardById(it) }

              sharder = DefaultShardManagerBuilder.createDefault(Config["token"])
                      .setActivityProvider { playing("on shard $it") }
                      .addEventListeners(Listener(), lavalink)
                      .setVoiceDispatchInterceptor(lavalink.voiceInterceptor)
                      .setAutoReconnect(true)
                      .setShardsTotal(1)
                      .setShards()
                      .setStatus(OnlineStatus.DO_NOT_DISTURB)
                      .build()

              lavalink = JdaLavalink(
                      "722834420226195517",
                      1
              ) { sharder.getShardById(it) }


              Config["node_password"].let { lavalink.addNode(URI(Config["node_uri"]), it) }
       }
       companion object {
              lateinit var lavalink: JdaLavalink
              lateinit var sharder: ShardManager
       }
}


