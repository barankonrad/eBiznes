package model

import play.api.libs.json._

case class Category(id: Int,
                    name: String)

object Category {
  implicit val categoryProduct: Format[Category] = Json.format[Category]
}
