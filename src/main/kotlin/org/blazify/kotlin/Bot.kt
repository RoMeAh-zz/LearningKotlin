package org.blazify.kotlin

import lavalink.client.io.jda.JdaLavalink
import lavalink.client.io.jda.JdaLink
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity.playing
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager
import java.net.URI


fun main() {
       Bot().run()
}

class Bot {
       var lavalink: JdaLavalink? = null
       fun run() {
              val sharder: ShardManager = DefaultShardManagerBuilder.createDefault(Config["token"])
                      .setActivity(playing("On Shard 1"))
                      .addEventListeners(Listener())
                      .setAutoReconnect(true)
                      .setShardsTotal(1)
                      .setShards()
                      .setStatus(OnlineStatus.DO_NOT_DISTURB)
                      .build()
              lavalink = JdaLavalink(
                      "722834420226195517",
                      sharder.shardsTotal
              ) { sharder.getShardById(it) }
              lavalink?.addNode(URI("http://localhost:2333"), "youshallnotpass")
       }
}



