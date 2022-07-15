package trafficdata.logic

import org.scalatest.flatspec.AnyFlatSpec
import trafficdata.logic.Model.Measurement
import trafficdata.logic.Model.Node
import trafficdata.logic.Model.Root
import trafficdata.logic.Model.TrafficMeasurement

class TrafficGraphSpec extends AnyFlatSpec {
  private val graph = {
    val m1 = Measurement(
      startAvenue = "A",
      startStreet = "1",
      transitTime = 2.33,
      endAvenue = "A",
      endStreet = "2",
    )
    val m2 = Measurement(
      startAvenue = "A",
      startStreet = "2",
      transitTime = 1.13,
      endAvenue = "B",
      endStreet = "1",
    )
    val m3 = Measurement(
      startAvenue = "A",
      startStreet = "1",
      transitTime = 5.33,
      endAvenue = "B",
      endStreet = "1",
    )
    val m4 = Measurement(
      startAvenue = "A",
      startStreet = "2",
      transitTime = 5.33,
      endAvenue = "B",
      endStreet = "3",
    )
    val m5 = Measurement(
      startAvenue = "A",
      startStreet = "1",
      transitTime = 5.33,
      endAvenue = "B",
      endStreet = "3",
    )

    val tm = TrafficMeasurement(
      measurementTime = 23111,
      measurements = List(m1, m2, m3, m4, m5),
    )
    val root = Root(trafficMeasurements = List(tm))
    TrafficGraph(root, WeightCalculationStrategies.AverageStrategy)
  }

  "TrafficGraph" should "find the shortest path" in {
    val result = graph.shortestPath("A", "1", "B", "1")
    assert(result == List(Node("A", "1"), Node("A", "2"), Node("B", "1")))
  }
}
