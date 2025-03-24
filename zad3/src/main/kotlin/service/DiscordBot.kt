package lab.service

import dev.kord.common.entity.PresenceStatus
import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import lab.model.Product
import lab.repository.MockedRepository.categories
import lab.repository.MockedRepository.products

private val botToken: String =
    System.getenv("DISCORD_BOT_TOKEN") ?: error("Could not acquire DISCORD_WEBHOOK_ID")

suspend fun startBot() {
    val client = Kord(botToken)

    client.on<MessageCreateEvent> {
        if (isAuthorBot()) return@on

        answerIfGreetings()
        answerIfAskingCategory()
        answerIfAskingForProductsOfGivenCategory()
        answerIfUserThanks()
    }

    client.login {
        presence {
            status = PresenceStatus.Online
        }
        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent
    }
}

private suspend fun MessageCreateEvent.answerIfUserThanks() {
    if (message.content.matches(Regex("^[Dd]zi[eę]ki bocie.*$"))){
        message.channel.createMessage("Nie ma sprawy 🫶🏼")
    }
}

private suspend fun MessageCreateEvent.answerIfAskingForProductsOfGivenCategory() {
    val regex = Regex("^bo(t|cie) produkty z `(.*)`$")
    if (message.content.matches(regex)) {
        val matchResult = regex.find(message.content)

        matchResult?.let { match ->
            val category = match.groupValues[2]
            val foundProducts: List<Product> =
                products.filter { product -> product.category.name.equals(category, true) }
            val formattedProducts =
                foundProducts.joinToString("\n") { "- ${it.name}, ${"%.2f".format(it.price)}$" }

            message.channel.createMessage("Jasne! Oto one:\n```$formattedProducts```")
        }
    }
}

private suspend fun MessageCreateEvent.answerIfAskingCategory() {
    if (message.content.matches(Regex("^bo(t|cie) kategorie$"))) {
        val categoryNames = categories.joinToString("\n") { "- ${it.name}" }
        message.channel.createMessage("Jasne! Oto one:\n```$categoryNames```")
    }
}

private suspend fun MessageCreateEvent.answerIfGreetings() {
    if (message.content.matches(Regex("^[Hh]ej bo(t|cie).*\$"))) {
        message.channel.createMessage("Hej ${message.author?.mention}!")
    }
}

private fun MessageCreateEvent.isAuthorBot(): Boolean {
    return message.author?.isBot == true
}