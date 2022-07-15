package trafficdata

object Exceptions {
  abstract class TrafficException(msg: String) extends Exception(msg)
  case class CommandException(msg: String) extends TrafficException(msg)
  case class ParserException(msg: String) extends TrafficException(msg)
}
