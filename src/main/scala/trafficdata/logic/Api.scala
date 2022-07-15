package trafficdata.logic

import io.circe.Json
import trafficdata.Exceptions.CommandException
import trafficdata.generic.FileReader

import scala.util.Failure
import scala.util.Try

object Api {
  def calculateTraffic(args: Array[String]): Try[Json] =
    if (args.length >= 5) {
      val Array(dataPath, startAvenue, startStreet, endAvenue, endStreet, _ @_*) = args
      for {
        file <- FileReader.readFile(dataPath)
        data <- JsonParser.parseJson(file)
        graph = TrafficGraph(data, WeightCalculationStrategies.AverageStrategy)
        path = graph.shortestPath(startAvenue, startStreet, endAvenue, endStreet)
        distance = graph.calculateTransitTime(path)
        result <- JsonParser.getTrafficOutput(path, distance)
      } yield result
    }
    else
      Failure(CommandException(s"Incorrect amount of params: ${args.length}"))
}
