package lab.service

import dev.kord.common.entity.PresenceStatus
import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent

private val botToken: String =
    System.getenv("DISCORD_BOT_TOKEN") ?: error("Could not acquire DISCORD_WEBHOOK_ID")

suspend fun startBot() {
    val client = Kord(botToken)

    client.on<MessageCreateEvent> {
        if (isAuthorBot()) return@on

        answerIfGreetings()
    }

    client.login {
        presence{
            status = PresenceStatus.Online
        }
        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent
    }
}

private suspend fun MessageCreateEvent.answerIfGreetings() {
    if (message.content.matches(Regex("^[Hh]ej bocie.*\$"))) {
        message.channel.createMessage("Hej ${message.author?.mention}!")
    }
}

private fun MessageCreateEvent.isAuthorBot(): Boolean {
    return message.author?.isBot == true
}