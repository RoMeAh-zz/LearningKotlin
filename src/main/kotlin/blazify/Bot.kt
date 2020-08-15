package blazify

import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity.playing
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager
import net.dv8tion.jda.api.utils.cache.CacheFlag


fun main() {
       Bot().run()
}


class Bot {
       fun run() {
              sharder = DefaultShardManagerBuilder
                      .createDefault(
                              Config["token"],
                              GatewayIntent.GUILD_MESSAGES,
                              GatewayIntent.GUILD_MEMBERS,
                              GatewayIntent.GUILD_VOICE_STATES,
                              GatewayIntent.GUILD_EMOJIS
                      )
                      .disableCache(
                              CacheFlag.CLIENT_STATUS,
                              CacheFlag.ACTIVITY
                      )
                      .enableCache(
                              CacheFlag.VOICE_STATE
                      )
                      .setActivityProvider {
                             playing("on shard $it")
                      }
                      .addEventListeners(
                              Listener()
                      )
                      .setAutoReconnect(true)
                      .setShardsTotal(1)
                      .setShards()
                      .setStatus(
                              OnlineStatus.DO_NOT_DISTURB
                      )
                      .build()
       }
       companion object {
              lateinit var sharder: ShardManager
       }
}


