package com.example

import com.jessecorbett.diskord.bot.*
import com.jessecorbett.diskord.bot.interaction.interactions
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import java.util.UUID

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

data class UserEvent(val eventId: UUID, val userId: String?, val name: String?, val description: String?,
                     val playerCount: String?, val date: String?, val time: String?)

suspend fun main() {

    val events = mutableMapOf<UUID, UserEvent>()

    bot(BOT_TOKEN) {
        // Modern interactions API for slash commands, user commands, etc
        interactions {
            slashCommand("user", "Access") {
                val message by stringParameter("message", "Add an event description", optional = false)
                callback {
                    val member = this.command.member?.user?.id
                    val eventId = UUID.randomUUID()
                    events[eventId] = UserEvent(eventId, member, name, description, playerCount, date, time)
                    respond {
                        content = "Event created."
                    }
                }
            }

            slashCommand("events", "List event") {
                callback {
                    respond {
                        events.values.forEach { content += it.name + "\n"}
                    }
                }
            }

        }
    }

}