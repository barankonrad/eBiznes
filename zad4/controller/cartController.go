package controller

import (
	"fmt"
	"github.com/labstack/echo/v4"
	"gorm.io/gorm"
	"myApp/model"
	"net/http"
	"strconv"
)

type CartController struct {
	Database *gorm.DB
}

func NewCartController(db *gorm.DB) *CartController {
	return &CartController{Database: db}
}

func (cc *CartController) Get(c echo.Context) error {
	idParam := c.QueryParam("id")
	if idParam == "" {
		return cc.GetAll(c)
	}
	return cc.GetById(c, idParam)
}

func (cc *CartController) GetById(c echo.Context, idStr string) error {
	id, err := strconv.Atoi(idStr)
	if err != nil {
		return c.JSON(http.StatusBadRequest, map[string]string{"error": "ID not numeric"})
	}

	var cart model.Cart
	if err := cc.Database.Preload("Items.Product").First(&cart, id).Error; err != nil {
		return c.JSON(http.StatusNotFound, map[string]string{"error": fmt.Sprintf("Cart with ID %d not found", id)})
	}

	return c.JSON(http.StatusOK, cart)
}

func (cc *CartController) GetAll(c echo.Context) error {
	var carts []model.Cart
	if err := cc.Database.Preload("Items.Product").Find(&carts).Error; err != nil {
		return c.JSON(http.StatusInternalServerError, err)
	}
	return c.JSON(http.StatusOK, carts)
}

func (cc *CartController) Add(c echo.Context) error {
	cart := &model.Cart{}
	if err := cc.Database.Create(cart).Error; err != nil {
		return c.JSON(http.StatusInternalServerError, err)
	}
	return c.JSON(http.StatusCreated, cart)
}

func (cc *CartController) Delete(c echo.Context) error {
	id, err := strconv.Atoi(c.QueryParam("id"))
	if err != nil {
		return c.JSON(http.StatusBadRequest, map[string]string{"error": "Invalid ID"})
	}

	if err := cc.Database.Delete(&model.Cart{}, id).Error; err != nil {
		return c.JSON(http.StatusInternalServerError, err)
	}

	return c.JSON(http.StatusOK, map[string]string{"message": fmt.Sprintf("Cart %d deleted", id)})
}

func (cc *CartController) AddItem(c echo.Context) error {
	var item model.CartItem
	if err := c.Bind(&item); err != nil {
		return c.JSON(http.StatusBadRequest, err)
	}

	if err := cc.Database.Create(&item).Error; err != nil {
		return c.JSON(http.StatusInternalServerError, err)
	}

	return c.JSON(http.StatusCreated, item)
}

func (cc *CartController) UpdateItemQuantity(c echo.Context) error {
	var input model.CartItem
	if err := c.Bind(&input); err != nil {
		return c.JSON(http.StatusBadRequest, err)
	}

	var item model.CartItem
	err := cc.Database.Where("cart_id = ? AND product_id = ?", input.CartID, input.ProductID).First(&item).Error
	if err != nil {
		return c.JSON(http.StatusNotFound, map[string]string{"error": "Item not found in cart"})
	}

	item.Quantity = input.Quantity
	if err := cc.Database.Save(&item).Error; err != nil {
		return c.JSON(http.StatusInternalServerError, err)
	}

	return c.JSON(http.StatusOK, item)
}

func (cc *CartController) RemoveItem(c echo.Context) error {
	var input model.CartItem
	if err := c.Bind(&input); err != nil {
		return c.JSON(http.StatusBadRequest, err)
	}

	if err := cc.Database.Where("cart_id = ? AND product_id = ?", input.CartID, input.ProductID).Delete(&model.CartItem{}).Error; err != nil {
		return c.JSON(http.StatusInternalServerError, err)
	}

	return c.JSON(http.StatusOK, map[string]string{"message": "Item removed from cart"})
}