package services
import models.*
import models.Todo
import java.io.PrintWriter
import java.io.FileWriter
import scala.io.Source

object TodoService {
  val TODO_CSV = "todo.csv"

  def getTodos: List[Todo] = {
    val fileSource = Source.fromFile(TODO_CSV)

    try {
      fileSource
        .getLines
        .toList
        .zipWithIndex
        .flatMap { case (line, index) => {
            line.split(",").toList match {
              case title :: completedString :: Nil => Some(Todo(index + 1, title, completedString.toBoolean))
              case _ => None
            }
          }
        }
    } finally {
      fileSource.close()
    }
  }

  def todosToString(todos: List[Todo]): String = {
    todos
      .map { todo =>
        s"${todo.id}. ${todo.title} | ${if (todo.completed) "✅" else "❌"}"
      }
      .mkString("\n")
  }

  def addTodo(title: String) = {
    val writer = FileWriter(TODO_CSV, true)

    try {
      writer.write(s"\n${title},false")
    } finally {
      writer.close()
    }
  }

  def removeTodo(id: Int) = {
    val fileSource = Source.fromFile(TODO_CSV)

    var removedTodoTitle: String = null

    val content = try {
      fileSource
        .getLines
        .toList
        .zipWithIndex
        .map(pair => {
          if (pair{1} + 1 == id) {
            removedTodoTitle = pair{0}.split(","){0}
          }
          pair
        })
        .filter { case (_, i) => i + 1 != id }
        .map { case (line, _) => line }
        .mkString("\n")
    } finally {
      fileSource.close()
    }

    val writer = FileWriter(TODO_CSV, false)

    try {
      writer.write(content)
    } finally {
      writer.close()
    }

    removedTodoTitle
  }

  def updateTodoStatus(id: Int, isComplete: Boolean) = {
    val fileSource = Source.fromFile(TODO_CSV)

    var updatedTodoTitle: String = null

    val lines = try {
      fileSource
        .getLines
        .toList
        .zipWithIndex
        .map { case (line, index) => {
            if (index == id - 1) {
              line.split(",").toList match {
                case title :: _ :: Nil => {
                  updatedTodoTitle = title
                  s"${title},${isComplete}"
                }
                case _ => line
              }
            } else {
              line
            }
          }
        }
    } finally {
      fileSource.close()
    }

    val writer = FileWriter(TODO_CSV, false)

    try {
      writer.write(lines.mkString("\n"))
    } finally {
      writer.close()
    }

    updatedTodoTitle
  }
}
