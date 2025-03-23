package lab.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import lab.service.DiscordService

fun Application.configureDiscordRouting() {
    val discordService = DiscordService()

    routing {
        post("/sendMessage") {
            val message = call.receive<String>()
            val responseCode = discordService.sendMessage(message)
            if (responseCode in 200..299) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(
                    HttpStatusCode.InternalServerError, "Error while sending message: $responseCode"
                )
            }
        }
    }
}