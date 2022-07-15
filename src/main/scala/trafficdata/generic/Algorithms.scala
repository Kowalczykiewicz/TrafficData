package trafficdata.generic

import scala.annotation.tailrec

object Algorithms {
  type GraphType[N] = N => Map[N, Double]

  def shortestPathTree[N](g: GraphType[N])(source: N): (Map[N, Double], Map[N, N]) = {
    @tailrec
    def traverse(
        currentNodes: Set[N],
        costMap: Map[N, Double],
        predMap: Map[N, N],
      ): (Map[N, Double], Map[N, N]) =
      if (currentNodes.isEmpty)
        (costMap, predMap)
      else {
        val node = currentNodes.minBy(costMap)
        val cost = costMap(node)
        val nextNodes = for {
          (n, c) <- g(node) if cost + c < costMap.getOrElse(n, Double.MaxValue)
        } yield n -> (cost + c)
        val nextActiveNodes = currentNodes - node ++ nextNodes.keys
        val predsMap = nextNodes.view.mapValues(_ => node)
        traverse(nextActiveNodes, costMap ++ nextNodes, predMap ++ predsMap)
      }

    traverse(Set(source), Map(source -> 0.0), Map.empty)
  }

  def shortestPath[N](g: GraphType[N])(source: N, target: N): List[N] = {
    val spt = shortestPathTree(g)(source)._2
    if (spt.contains(target) || source == target)
      buildPath(target)(spt.get)
    else Nil
  }

  def buildPath[N](x: N)(f: N => Option[N]): List[N] = {
    @tailrec
    def path(x: N, acc: List[N]): List[N] = f(x) match {
      case None => x :: acc
      case Some(y) => path(y, x :: acc)
    }
    path(x, List.empty)
  }
}
