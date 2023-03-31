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

	//1.delete seq, 작성자같은지 확인
	//2.같으면 삭제 다르면 에러
	conn.Delete(&DB.Note{}, deleteSeq.noteSeq)
}
