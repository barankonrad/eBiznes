package model

import play.api.libs.json._

case class CartItem(id: Int,
                    quantity: Int)

object CartItem {
  implicit val format: Format[CartItem] = Json.format[CartItem]
}