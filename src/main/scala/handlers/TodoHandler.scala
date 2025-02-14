package handlers
import services.TodoService.*

object TodoHandler {
  def handleListTodos = "Current Todos:\n" + todosToString(getTodos)

  def handleAddTodo(title: String) = {
    addTodo(title)
    s"Added \"${title}\""
  }

  def handleCompleteTodo(id: Int) = s"Completed \"${updateTodoStatus(id, true)}\""

  def handleIncompleteTodo(id: Int) = s"Marked \"${updateTodoStatus(id, false)}\" as incomplete"

  def handleRemoveTodo(id: Int) = s"Removed \"${removeTodo(id)}\""
}