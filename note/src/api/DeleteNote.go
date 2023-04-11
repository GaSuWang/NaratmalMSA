package api

import (
	"github.com/gin-gonic/gin"
	DB "note/src/db"
)

type deleteNoteReq struct {
	noteSeq int64
}

func DeleteNote(context *gin.Context) {
	deleteSeq := deleteNoteReq{}
	context.BindJSON(deleteSeq)
	conn := DB.GetInstance()
	var note DB.Note
	//1.delete seq, 작성자같은지 확인
	conn.Where("note_seq = ?", deleteSeq.noteSeq).Find(&note)
	if note.UserEmail != context.GetHeader("Authorization-Email") {
		return
	}
	//2.같으면 삭제
	conn.Delete(&DB.Note{}, deleteSeq.noteSeq)
}
