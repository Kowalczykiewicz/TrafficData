package trafficdata.logic

import trafficdata.logic.Model.Measure

object WeightCalculationStrategies {
  trait WeightCalculationStrategy { def calculate(measure: List[Measure]): Double }

  case object AverageStrategy extends WeightCalculationStrategy {
    override def calculate(measures: List[Measure]): Double = {
      val sum = measures.foldLeft(0.0) { (acc, elem) =>
        acc + elem.weight
      }
      sum / measures.length
    }
  }
  // TODO add more
}
