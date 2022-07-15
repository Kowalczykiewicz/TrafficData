package trafficdata.logic

import org.scalatest.flatspec.AnyFlatSpec
import trafficdata.logic.JsonParser.parseJson

class JsonParserSpec extends AnyFlatSpec {
  "JsonParser" should "parse correctly" in {
    val jsonString =
      """
        |  {
        |    "trafficMeasurements": [
        |      {
        |        "measurementTime": 83452,
        |        "measurements": [
        |          {
        |            "startAvenue": "A",
        |            "startStreet": "1",
        |            "transitTime": 59.57363899660943,
        |            "endAvenue": "A",
        |            "endStreet": "2"
        |          },
        |          {
        |            "startAvenue": "A",
        |            "startStreet": "2",
        |            "transitTime": 40.753916740023314,
        |            "endAvenue": "A",
        |            "endStreet": "3"
        |          }
        |        ]
        |      },
        |      {
        |        "measurementTime": 83552,
        |        "measurements": [
        |          {
        |            "startAvenue": "A",
        |            "startStreet": "2",
        |            "transitTime": 51.57363899660943,
        |            "endAvenue": "A",
        |            "endStreet": "3"
        |          }
        |        ]
        |      }
        |    ]
        |  }
        |""".stripMargin

    val parsed = parseJson(jsonString)

    assert(parsed.isSuccess)
  }
}
