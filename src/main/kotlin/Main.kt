package com.example

import com.jessecorbett.diskord.bot.*
import com.jessecorbett.diskord.bot.interaction.interactions
import com.jessecorbett.diskord.util.*
import kotlinx.serialization.builtins.UByteArraySerializer

/*
 * This can be replaced with any method to load the bot token.  This specific method is provided only for convenience
 * and as a way to prevent accidental disclosure of bot tokens.
 *
 * Alternative methods might include reading from the environment, using a system property, or reading from the CLI.
 */


private val BOT_TOKEN = try {
    ClassLoader.getSystemResource("bot-token.txt").readText().trim()
} catch (error: Exception) {
    throw RuntimeException("Failed to load bot token. Make sure to create a file named bot-token.txt in" +
            " src/main/resources and paste the bot token into that file.", error)
}


suspend fun main() {

    val events = mutableMapOf<String, String>()

    bot(BOT_TOKEN) {
        // Modern interactions API for slash commands, user commands, etc
        interactions {
            slashCommand("user", "Access") {
                val message by stringParameter("message", "Add an event description", optional = false)
                callback {
                    val member = this.command.member?.user?.id
                    
                    respond {
                        content = "$member said $message"
                    }
                }
            }
        }
    }

}