package lab.repository

import kotlinx.serialization.json.Json
import lab.model.Category
import java.io.File

object MockedRepository {
    var categories: Set<Category> = emptySet()

    init {
        println("Initializing MockedRepository...")
        initializeCategories(File("src/main/resources/categoryExamples.json"))
    }

    private fun initializeCategories(file: File) {
        categories = loadFromFile(file)
    }

    private inline fun <reified T> loadFromFile(file: File): Set<T> {
        return try {
            val jsonContent = file.readText()
            val data: List<T> = Json.decodeFromString(jsonContent)
            data.toSet().also {
                println("Successfully loaded ${it.size} ${T::class.simpleName?.lowercase()}")
            }
        } catch (e: Exception) {
            println("Failed to load ${T::class.simpleName}: ${e.message}")
            emptySet()
        }
    }
}