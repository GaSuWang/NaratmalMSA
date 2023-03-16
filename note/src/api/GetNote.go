package api

import "github.com/gin-gonic/gin"
import (
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

type Note struct {
	gorm.Model
	noteSeq int64
}

func GetNote(context *gin.Context) {
	//1. location 가져오기
	location := context.Param("location")
	db, err := gorm.Open(mysql.Open("note.db"), &gorm.Config{})
	if err != nil {
		panic("failed to connect database")
	}
	var notes []Note
	db.Where("location LIKE ?", location).Find(&notes)
	//2. DB에서 location 기반으로 select
	//3. 결과 담아서 반환
}
