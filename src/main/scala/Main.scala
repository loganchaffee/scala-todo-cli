import handlers.TodoHandlers.*
object Main {
  def main(args: Array[String]) = {
    val output = args.toList match {
      case Nil => handleHelp
      case "help" :: _ => handleHelp
      case "list" :: _ => handleListTodos
      case "add" :: title :: Nil => handleAddTodo(title)
      case "remove" :: id :: Nil => handleRemoveTodo(id.toInt)
      case "complete" :: id :: Nil => handleCompleteTodo(id.toInt)
      case "incomplete" :: id :: Nil => handleIncompleteTodo(id.toInt)
      case unknownArgument :: _ => s"\"${unknownArgument}\" is not a valid command"
    }

    println(output)
  }
}