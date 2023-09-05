package com.example

import com.jessecorbett.diskord.bot.*
import com.jessecorbett.diskord.bot.interaction.interactions
import com.jessecorbett.diskord.util.*

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

            slashCommand("event", "Makes an event") {
                val message by stringParameter("message", "The message", optional = false)

                callback {
                    events[message!!] = message!!

                    respond {
                        content = events[message]
                    }
                }
            }

            userCommand("event", "Makes an event") {
                val message by stringParameter("message", "The message", optional = false)

                callback {
                    events[message!!] = message!!
                    var = botUser.id
                    message.
                    respond {
                        content = events[message]
                    }
                }
            }

        }
    }

}