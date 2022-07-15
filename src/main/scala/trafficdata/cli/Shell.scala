package trafficdata.cli

import trafficdata.generic.Logger
import trafficdata.logic.Api

import scala.util.Failure
import scala.util.Success

object Shell extends App {
  System.exit(Api.calculateTraffic(args) match {
    case Success(result) =>
      Logger.info(result.toString)
      0
    case Failure(e) =>
      Logger.error(e)
      -1
  })
}
