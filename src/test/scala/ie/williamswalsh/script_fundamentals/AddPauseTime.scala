package ie.williamswalsh.script_fundamentals

// Underscore is wildcard char

import io.gatling.core.Predef._
import io.gatling.http.Predef._

// Setting the duration to: 1000.milliseconds
import scala.concurrent.duration.DurationInt

class AddPauseTime extends Simulation {
  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  //  2) scenario
  val scn = scenario("Video Game DB - 3 calls")
    .exec(http("Get all games").get("/videogame"))
    .pause(1) // Pause for 1 second

    .exec(http("Get specific game").get("/videogame/5"))
    .pause(1,3) // Pause randomly between 1 & 3 seconds

    .exec(http("Get all games").get("/videogame"))
    .pause(1000.milliseconds)

    .pause(100.milliseconds,200.milliseconds)

  setUp(
    scn.inject(atOnceUsers(1))
      .protocols(httpProtocol)
  )
}
