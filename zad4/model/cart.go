package model

import "gorm.io/gorm"

type Cart struct {
	gorm.Model
	Items []CartItem `gorm:"foreignKey:CartID" json:"items"`
}