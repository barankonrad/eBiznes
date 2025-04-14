package main

import (
	"github.com/labstack/echo/v4"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"myApp/controller"
	"myApp/model"
)

func main() {
	db, err := gorm.Open(sqlite.Open("local.db"), &gorm.Config{})
	if err != nil {
		return
	}

	err = db.AutoMigrate(&model.Product{}, &model.Cart{}, &model.CartItem{})
	if err != nil {
		return
	}

	route := echo.New()

	productController := controller.NewProductController(db)
	route.GET("/product", productController.Get)
	route.POST("/product", productController.Add)
	route.PUT("/product", productController.Put)
	route.DELETE("/product", productController.Delete)

	cartController := controller.NewCartController(db)
	route.GET("/cart", cartController.Get)
	route.POST("/cart", cartController.Add)
	route.DELETE("/cart", cartController.Delete)
	route.POST("/cart/item", cartController.AddItem)
	route.PUT("/cart/item", cartController.UpdateItemQuantity)
	route.DELETE("/cart/item", cartController.RemoveItem)

	route.Logger.Fatal(route.Start(":9090"))
}
