import commands.*
import handlers.TodoHandler.*

object Main {
  def getCommandFromArgs(args: Array[String]) = {
    args.toList match {
      case "list" :: Nil => Some(List)
      case "add" :: todo :: Nil => Some(Add(todo))
      case "remove" :: id :: Nil => Some(Remove(id.toInt))
      case "complete" :: id :: Nil => Some(Complete(id.toInt))
      case "incomplete" :: id :: Nil => Some(Incomplete(id.toInt))
      case _ => None
    }
  }

  def main(args: Array[String]): Unit = {
    getCommandFromArgs(args)
      .map { command => command match {
          case List => handleListTodos
          case Add(title) => handleAddTodo(title)
          case Remove(id) => handleRemoveTodo(id)
          case Complete(id) => handleCompleteTodo(id)
          case Incomplete(id) => handleIncompleteTodo(id)
        }
      }
      .map(message => println(message))
  }
}