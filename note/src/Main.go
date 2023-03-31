package main

//import "fmt"
import (
	"github.com/gin-gonic/gin"
	DB "note/src/db"
)
import "note/src/api"

func main() {
	DB.GetInstance()
	engine := gin.Default()
	note := engine.Group("/note")
	{
		note.GET("/:location", api.GetNote)
		note.POST("", api.RegistNote)
		note.DELETE("", api.DeleteNote)
	}
	engine.Run(":8181")
}
