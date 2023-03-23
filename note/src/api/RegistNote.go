package api

import (
	"github.com/gin-gonic/gin"
	DB "note/src/db"
)

type NoteReq struct {
}

func RegistNote(context *gin.Context) {
	//1. 헤더에서 userEmail 가져오기
	userEamil := context.GetHeader("Authorization-Email")
	//userEmail 바탕으로 userService에 이름 조합받기
	requestUserInfo(userEamil)
	//2. 전달받은 note 정보 열기
	var noteReq NoteReq
	context.BindJSON(&noteReq)

	conn := DB.GetInstance()
	note := Note{}
	//note.
	conn.Save(&note)

	return

	//4. 결과 반환
}

func requestUserInfo(email string) string {
	return email
}
