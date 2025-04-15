package controller

import (
	"fmt"
	"github.com/labstack/echo/v4"
	"gorm.io/gorm"
	"myApp/model"
	"net/http"
	"strconv"
)

type ProductController struct {
	Database *gorm.DB
}

func NewProductController(database *gorm.DB) *ProductController {
	return &ProductController{Database: database}
}

func (pc *ProductController) Get(c echo.Context) error {
	param := c.QueryParam("id")
	if param == "" {
		return pc.GetAll(c)
	}
	return pc.GetById(c, param)
}

func (pc *ProductController) GetById(c echo.Context, idStr string) error {
	id, err := strconv.Atoi(idStr)
	if err != nil {
		return c.JSON(http.StatusBadRequest, map[string]string{"error": "ID not numeric"})
	}

	var product model.Product
	if err := pc.Database.Preload("Category").First(&product, id).Error; err != nil {
		return c.JSON(http.StatusNotFound, map[string]string{"error": fmt.Sprintf("Product with ID %d not found", id)})
	}

	return c.JSON(http.StatusOK, product)
}

func (pc *ProductController) GetAll(c echo.Context) error {
	var products []model.Product
	if err := pc.Database.Preload("Category").Find(&products).Error; err != nil {
		return c.JSON(http.StatusInternalServerError, err)
	}
	return c.JSON(http.StatusOK, products)
}

func (pc *ProductController) Add(c echo.Context) error {
	product := new(model.Product)
	if err := c.Bind(product); err != nil {
		return c.JSON(http.StatusBadRequest, err)
	}
	if err := pc.Database.Create(product).Error; err != nil {
		return c.JSON(http.StatusInternalServerError, err)
	}
	// Załaduj kategorię po zapisaniu, żeby była zwrócona
	if err := pc.Database.Preload("Category").First(product, product.ID).Error; err != nil {
		return c.JSON(http.StatusInternalServerError, err)
	}
	return c.JSON(http.StatusCreated, product)
}

func (pc *ProductController) Put(c echo.Context) error {
	id, err := strconv.Atoi(c.QueryParam("id"))
	if err != nil {
		return c.JSON(http.StatusBadRequest, map[string]string{"error": "Invalid ID"})
	}

	var product model.Product
	if err := pc.Database.First(&product, id).Error; err != nil {
		return c.JSON(http.StatusNotFound, map[string]string{"error": fmt.Sprintf("Product with ID %d not found", id)})
	}

	if err := c.Bind(&product); err != nil {
		return c.JSON(http.StatusBadRequest, err)
	}

	if err := pc.Database.Save(&product).Error; err != nil {
		return c.JSON(http.StatusInternalServerError, err)
	}

	if err := pc.Database.Preload("Category").First(&product, product.ID).Error; err != nil {
		return c.JSON(http.StatusInternalServerError, err)
	}

	return c.JSON(http.StatusOK, product)
}

func (pc *ProductController) Delete(c echo.Context) error {
	id, err := strconv.Atoi(c.QueryParam("id"))
	if err != nil {
		return c.JSON(http.StatusBadRequest, map[string]string{"error": "Invalid ID"})
	}

	var product model.Product
	if err := pc.Database.First(&product, id).Error; err != nil {
		return c.JSON(http.StatusNotFound, map[string]string{"error": fmt.Sprintf("Product with ID %d not found", id)})
	}

	if err := pc.Database.Delete(&product).Error; err != nil {
		return c.JSON(http.StatusInternalServerError, err)
	}

	return c.JSON(http.StatusOK, product)
}