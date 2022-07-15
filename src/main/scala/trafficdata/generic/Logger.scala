package trafficdata.generic

object Logger {
  def error(message: String): Unit =
    System.err.println(s"[ERROR] $message")

  def error(exception: Throwable): Unit =
    error(exception.getMessage, exception)

  def error(message: String, exception: Throwable): Unit =
    error(s"Exception: $message")

  def warn(message: String): Unit =
    System.err.println(s"[WARN ] $message")

  def info(message: String): Unit =
    println(s"[INFO ] $message")

  def debug(message: Any): Unit =
    println(s"[DEBUG] $message")
}
