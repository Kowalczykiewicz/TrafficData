package trafficdata.logic

import trafficdata.generic.Algorithms
import trafficdata.logic.Model.Measure
import trafficdata.logic.Model.Node
import trafficdata.logic.Model.Root
import trafficdata.logic.WeightCalculationStrategies.WeightCalculationStrategy

class TrafficGraph(struct: Node => Map[Node, Double]) {
  def shortestPath(
      startAvenue: String,
      startStreet: String,
      endAvenue: String,
      endStreet: String,
    ): List[Node] = {
    val fromNode = Node(startAvenue, startStreet)
    val toNode = Node(endAvenue, endStreet)
    Algorithms.shortestPath(struct)(fromNode, toNode)
  }

  def calculateTransitTime(nodes: List[Node]): Double = nodes match {
    case Nil | _ :: Nil => 0.0
    case _ =>
      val path = nodes.dropRight(1) zip nodes.tail
      path.foldLeft(0.0) { (acc, pathNodes) =>
        acc + struct(pathNodes._1)(pathNodes._2)
      }
  }
}

object TrafficGraph {
  def apply(data: Root, strategy: WeightCalculationStrategy): TrafficGraph = {
    case class Edge(from: Node, to: Node)

    val edgeAllMeasuresMap: Map[Edge, List[Measure]] = {
      val edgeSingleMeasures = {
        def buildEdgeSingleWeightMap(tm: Model.TrafficMeasurement) =
          tm.measurements
            .map { m =>
              val from = Node(m.startAvenue, m.startStreet)
              val to = Node(m.endAvenue, m.endStreet)
              (Edge(from, to), m.transitTime)
            }
            .toMap

        data
          .trafficMeasurements
          .map(tm => (buildEdgeSingleWeightMap(tm), tm.measurementTime))
          .map {
            case (edgeWeightMap, time) => edgeWeightMap.view.mapValues(mt => Measure(time, mt))
          }
      }

      edgeSingleMeasures.foldLeft(Map[Edge, List[Measure]]()) { (acc, elem) =>
        elem.map {
          case (key, measure) =>
            acc.get(key) match {
              case Some(measures) => (key, measures :+ measure)
              case None => (key, List(measure))
            }
        }.toMap
      }
    }

    val struct = (node: Node) =>
      edgeAllMeasuresMap
        .view
        .mapValues(strategy.calculate)
        .filter {
          case (key, _) => key.from == node
        }
        .map {
          case (key, value) => (key.to, value)
        }
        .toMap

    new TrafficGraph(struct)
  }
}
