package ie.williamswalsh.script_fundamentals

// Underscore is wildcard char

import io.gatling.core.Predef._
import io.gatling.http.Predef._

// Setting the duration to: 1000.milliseconds
import scala.concurrent.duration.DurationInt

class CheckResponseCode extends Simulation {
  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  //  2) scenario
  val scn = scenario("Video Game DB - 3 calls")
    .exec(http("Get all games").get("/videogame")
//      Check if response code is 200
      .check(status.is(200)))

    .exec(http("Get specific game").get("/videogame/5"))
//      NOT WORKING
//      Check if response code is in a range
//    .check(status.is(200 to 210)))


  .exec(http("Get all games").get("/videogame")
    //  Check if response is not 400/500
    .check(status.not(400),status.not(500)))

//    If not equal test still runs but logs:
//  status.find.is(404), but actually found 200
//    .check(status.is(404)) -> 200 responded

  setUp(
    scn.inject(atOnceUsers(1))
      .protocols(httpProtocol)
  )
}
