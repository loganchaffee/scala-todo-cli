package services
import models.Todo
import java.io.{PrintWriter, FileWriter, File}
import scala.io.Source
import scala.util.Using

object TodoService {
  val TODO_CSV = "todo.csv"

  def getTodosFromCSV: List[Todo] = {
    val file = new File(TODO_CSV)

    if !file.exists then return List.empty

    Using(Source.fromFile(TODO_CSV)) { source =>
      source
        .getLines
        .toList
        .zipWithIndex
        .flatMap { case (line, index) =>
          line.split(",").toList match {
            case title :: completedString :: Nil => Some(Todo(index + 1, title, completedString.toBoolean))
            case _ => None
          }
        }
    }.getOrElse(List.empty)
  }

  def addTodo(title: String) = {
    Using(FileWriter(TODO_CSV, true)) { writer =>
      writer.write(s"${if new File(TODO_CSV).length > 0 then "\n" else ""}${title},false")
    }.getOrElse(throw new RuntimeException("Failed to write to file"))
  }

  def removeTodo(id: Int) = {
    val linesWithIndexes = Using(Source.fromFile(TODO_CSV)) { fileSource =>
      fileSource.getLines.toList.zipWithIndex
    }.getOrElse(List.empty)

    val (remainingLines, removedLines) = linesWithIndexes.partition { case (line, index) =>
      id != index + 1
    }

    Using(FileWriter(TODO_CSV, false)) { writer =>
      writer.write(remainingLines.map { case (line, _) => line }.mkString)
    }.getOrElse(throw new RuntimeException("Failed to write to file"))

    removedLines.map { case (line, _) => line }.headOption.map(line => line.split(",").toList.head)
  }

  def updateTodoStatus(id: Int, isComplete: Boolean) = {
    var updatedTodoTitle = ""

    val output = Using(Source.fromFile(TODO_CSV)) { fileSource =>
      fileSource
        .getLines
        .toList
        .zipWithIndex
        .foldLeft("") { (acc, curr) =>
          val (line, index) = curr

          if (index + 1 == id) {
            updatedTodoTitle = line.split(",").toList.head
            acc + s"${updatedTodoTitle},${isComplete}" + "\n"
          } else {
            acc + line + "\n"
          }
        }
    }.getOrElse("")

    Using(FileWriter(TODO_CSV, false)) { writer =>
      writer.write(output)
    }.getOrElse(throw new RuntimeException("Failed to write to file"))

    updatedTodoTitle
  }

  def _updateTodoStatus(id: Int, isComplete: Boolean) = {
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

  def todosToString(todos: List[Todo]): String = {
    todos
      .map { todo =>
        s"${todo.id}. ${if (todo.completed) "✅" else "❌"} ${todo.title} "
      }
      .mkString("\n")
  }
}
