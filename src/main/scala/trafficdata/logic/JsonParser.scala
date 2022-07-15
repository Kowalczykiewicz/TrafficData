package trafficdata.logic

import io.circe._
import io.circe.generic.semiauto._
import io.circe.parser._
import io.circe.syntax._
import trafficdata.Exceptions.ParserException
import trafficdata.logic.Model.Measurement
import trafficdata.logic.Model.Node
import trafficdata.logic.Model.TrafficOutput
import trafficdata.logic.Model.Root
import trafficdata.logic.Model.TrafficMeasurement

import scala.util.Failure
import scala.util.Success
import scala.util.Try

object JsonParser {
  def parseJson(jsonString: String): Try[Root] = {
    implicit val measurementDecoder: Decoder[Measurement] = deriveDecoder[Measurement]
    implicit val trafficDecoder: Decoder[TrafficMeasurement] = deriveDecoder[TrafficMeasurement]
    implicit val rootDecoder: Decoder[Root] = deriveDecoder[Root]

    val decoded = decode[Root](jsonString)

    decoded match {
      case Right(decodedJson) => Success(decodedJson)
      case Left(_) => Failure(ParserException("Invalid JSON object"))
    }
  }

  def getTrafficOutput(nodes: List[Node], distanceTime: Double): Try[Json] = {
    implicit val nodeEncoder: Encoder[Node] = deriveEncoder[Node]
    implicit val resultEncoder: Encoder[TrafficOutput] = deriveEncoder[TrafficOutput]

    nodes match {
      case Nil => println("Hisss"); Failure(ParserException("No traffic data"))
      case node :: Nil =>
        val summary = TrafficOutput(node, node, Nil, 0)
        Success(summary.asJson)
      case _ =>
        val summary = TrafficOutput(
          nodes.head,
          nodes.last,
          nodes.tail.dropRight(1),
          distanceTime,
        )
        Success(summary.asJson)
    }
  }
}
