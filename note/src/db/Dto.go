package db

type Note struct {
	NoteSeq        int64
	Title          string
	Content        string
	Color          string
	Location       string
	FontName       string
	FontFamilyName string
	FontPath       string
	FontSeq        int64
	UserEmail      string
	UserName       string
	UserNickname   string
}
