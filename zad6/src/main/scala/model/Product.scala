package model

import play.api.libs.json._

case class Product(id: Int,
                   name: String,
                   price: Double,
                   categoryId: Int)

object Product {
  implicit val productFormat: Format[Product] = Json.format[Product]
}