package trafficdata.logic

object Model {
  case class Node(avenue: String, street: String)

  case class Measure(time: Int, weight: Double)

  case class Root(trafficMeasurements: List[TrafficMeasurement])

  case class TrafficMeasurement(
      measurementTime: Int,
      measurements: List[Measurement],
    )

  case class Measurement(
      startAvenue: String,
      startStreet: String,
      transitTime: Double,
      endAvenue: String,
      endStreet: String,
    )

  case class TrafficOutput(
      start: Node,
      end: Node,
      roadSegments: List[Node],
      totalTransitTime: Double,
    )
}
