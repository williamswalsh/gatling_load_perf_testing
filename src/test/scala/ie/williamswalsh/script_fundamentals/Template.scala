package ie.williamswalsh.script_fundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class Template extends Simulation {
  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")


  val scn = scenario("")
    .exec(http("").get(""))
    .pause(1)

  setUp(
    scn.inject(atOnceUsers(1))
      .protocols(httpProtocol)
  )
}
