package trafficdata.generic

import java.io.BufferedReader
import java.io.{ FileReader => Reader }
import scala.util.Try
import scala.util.Using

object FileReader {
  def readFile(path: String): Try[String] =
    Using(new BufferedReader(new Reader(path))) { reader =>
      Iterator.continually(reader.readLine()).takeWhile(_ != null).mkString("")
    }
}
