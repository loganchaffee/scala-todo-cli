package commands

sealed trait Command
object List extends Command
case class Add(todo: String) extends Command
case class Remove(id: Int) extends Command
case class Complete(id: Int) extends Command
case class Incomplete(id: Int) extends Command