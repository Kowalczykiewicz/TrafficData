package trafficdata

import trafficdata.generic.Logger

object Exceptions {
  abstract class TrafficException(msg: String) extends Exception(msg)
  case class CommandException(msg: String) extends TrafficException(msg)
  case class ParserException(msg: String) extends TrafficException(msg)

  def unSafe[T](exception: TrafficException)(block: => T): T =
    try
      block
    catch {
      case e: Exception =>
        Logger.error(e)
        throw exception.initCause(e)
    }
}
