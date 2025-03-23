package lab.service

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.buildJsonObject
import lab.model.DiscordMessage


class DiscordService {
    companion object {
        const val BASE_URL = "https://discord.com/api/webhooks"
    }

    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }
    private val webhookId: String =
        System.getenv("DISCORD_WEBHOOK_ID") ?: error("Could not acquire DISCORD_WEBHOOK_ID")
    private val webhookToken: String =
        System.getenv("DISCORD_WEBHOOK_TOKEN") ?: error("Could not acquire DISCORD_WEBHOOK_TOKEN")

    private val webhookUrl = "$BASE_URL/$webhookId/$webhookToken"

    suspend fun sendMessage(message: String) : Int {
        val response = client.post(webhookUrl) {
            contentType(ContentType.Application.Json)
            setBody(DiscordMessage(message))
        }
        return response.status.value
    }
}