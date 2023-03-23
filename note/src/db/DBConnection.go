package db

import (
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

var connection *gorm.DB
var ERR error

func GetInstance() *gorm.DB {
	if connection == nil {
		connectDB()
	}
	return connection
}

func init() {
	if connection == nil {
		connectDB()
	}
}

func connectDB() {
	dsn := "msa:msa@tcp(127.0.0.1:7777)/msa_note?charset=utf8mb4&parseTime=True&loc=Local"
	connection, ERR = gorm.Open(mysql.Open(dsn), &gorm.Config{})
	if ERR != nil {
		panic("failed to connect database")
	}
}
