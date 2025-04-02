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

	err = db.AutoMigrate(&model.Product{})
	if err != nil {
		return
	}

	productController := controller.NewProductController(db)

	route := echo.New()
	route.GET("/product", productController.Get)
	route.POST("/product", productController.Add)
	route.PUT("/product", productController.Put)
	route.DELETE("/product", productController.Delete)

	route.Logger.Fatal(route.Start(":9090"))
}
