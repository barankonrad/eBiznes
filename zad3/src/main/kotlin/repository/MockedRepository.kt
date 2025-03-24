package lab.repository

import kotlinx.serialization.json.Json
import lab.model.Category
import lab.model.Product
import java.io.File

object MockedRepository {
    var categories: Set<Category> = emptySet()
    var products: Set<Product> = emptySet()

    init {
        println("Initializing MockedRepository...")
        categories = loadFromFile(File("src/main/resources/categoryExamples.json"))
        products = loadFromFile(File("src/main/resources/productExamples.json"))
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