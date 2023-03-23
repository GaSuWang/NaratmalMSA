package api

import (
	"github.com/gin-gonic/gin"
	DB "note/src/db"
)

func DeleteNote(context *gin.Context) {
	conn := DB.GetInstance()
	conn.Delete("a")
	//1.delete seq, 작성자같은지 확인
	//2.같으면 삭제 다르면 에러
}
