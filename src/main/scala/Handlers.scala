package handlers
import services.TodoService.*

object TodoHandlers {
  def handleListTodos = {
    val todoString = todosToString(getTodosFromCSV)

    if (todoString != "") "Current Todos:\n" + todoString else "No Todos"
  }

  def handleAddTodo(title: String) = {
    addTodo(title)

    s"Added \"${title}\""
  }

  def handleCompleteTodo(id: Int) = s"Completed \"${updateTodoStatus(id, true)}\""

  def handleIncompleteTodo(id: Int) = s"Marked \"${updateTodoStatus(id, false)}\" as incomplete"

  def handleRemoveTodo(id: Int) = {
    removeTodo(id).map(title => s"""Removed "$title"""").getOrElse("Invalid ID")
  }

  def handleHelp = """usage: todo [command] [argument]'

  |commands:
  |list                Print current todos
  |add "<title>"       Add a new todo (e.g., todo add "Wash Car")
  |complete <id>       Mark a todo as complete (e.g., todo complete 3)
  |incomplete <id>     Mark a todo as incomplete (e.g., todo incomplete 3)
  |remove <id>         Remove a todo from the list (e.g., todo remove 3)
  """.stripMargin
}