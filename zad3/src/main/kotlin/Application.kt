package lab

import io.ktor.server.application.*
import lab.route.configureDiscordRouting

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDiscordRouting()
}
