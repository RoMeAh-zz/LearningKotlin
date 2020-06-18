@file:Suppress("DEPRECATION")

package org.blazify.kotlin

import net.dv8tion.jda.api.JDABuilder
//import net.dv8tion.jda.api.entities.Activity


fun main () {
        JDABuilder()
                .setToken(Config["token"])
                //.setActivity(Activity.playing("around with kotlin..."))
                .addEventListeners(Listener())
                .build()
    }
