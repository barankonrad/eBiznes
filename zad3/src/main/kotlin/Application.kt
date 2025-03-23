package lab

import io.ktor.server.application.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import lab.route.configureDiscordRouting
import lab.service.startBot

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@OptIn(DelicateCoroutinesApi::class)
fun Application.module() {
    configureDiscordRouting()
    GlobalScope.launch {
        startBot()
    }
}
