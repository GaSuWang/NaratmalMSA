package api

import (
	"github.com/gin-gonic/gin"
	"net/http"
)
import (
	DB "note/src/db"
)

type NoteRes struct {
	padletList []DB.Note
}

func GetNote(context *gin.Context) {
	//1. location 가져오기
	location := "서울"
	conn := DB.GetInstance()
	var notes []DB.Note

	//2. DB에서 location 기반으로 select
	conn.Where("location LIKE ?", location).Find(&notes)
	//3. 결과 담아서 반환
	noteRes := NoteRes{notes}
	context.JSON(http.StatusOK, noteRes)
}
