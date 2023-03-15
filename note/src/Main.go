package main

//import "fmt"
import "github.com/gin-gonic/gin"
import "note/src/api"

func main() {
	engine := gin.Default()
	note := engine.Group("/note")
	{
		note.GET("/:location", api.GetNote)
		note.POST("", api.RegistNote)
		note.DELETE("/:location", api.DeleteNote)
	}
	engine.Run(":8181")
}
