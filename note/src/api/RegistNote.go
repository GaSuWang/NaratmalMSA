package api

import (
	"github.com/gin-gonic/gin"
	DB "note/src/db"
)

type NoteReq struct {
	title    string
	content  string
	color    string
	location string
	fontSeq  int64
}

func RegistNote(context *gin.Context) {
	//1. 헤더에서 userEmail 가져오기
	var note DB.Note
	note.UserEmail = context.GetHeader("Authorization-Email")

	//2. 전달받은 note 정보 열기
	var noteReq NoteReq
	context.BindJSON(&noteReq)
	conn := DB.GetInstance()
	makeNote(&note, &noteReq)

	//note.
	conn.Save(&note)

	return

	//4. 결과 반환
}

func makeNote(note *DB.Note, noteReq *NoteReq) {
	note.Title = noteReq.title
	note.Content = noteReq.content
	note.Location = noteReq.location
	note.Color = noteReq.color
	//폰트 정보 가져오기
	getUserInfo(note)
	getFontInfo(note)

	//사용자 정보 가져오기
}

func getUserInfo(note *DB.Note) {
	note.UserNickname = "abc"
	note.UserName = "aaa"
}

func getFontInfo(note *DB.Note) {
	note.FontPath = "aaa"
	note.FontName = "aaa"
	note.FontFamilyName = "aaa"
}

