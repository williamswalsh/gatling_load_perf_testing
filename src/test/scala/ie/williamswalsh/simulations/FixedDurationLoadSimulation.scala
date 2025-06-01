package ie.williamswalsh.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class FixedDurationLoadSimulation extends Simulation {
  val httpProtocol = http.baseUrl("http://videogamedb.uk/api")
    .acceptHeader("application/json")
//    .disableFollowRedirect

  def getAllVideoGames = {
    exec(http("Get all games").get("/videogame"))
  }

  def getSpecificVideoGame = {
    exec(http("Get single game").get("/videogame/1"))
  }

  val scn = scenario("Fixed duration load simulations")
    .forever {
      exec(getAllVideoGames);
    }

  setUp(
    scn.inject(
      nothingFor(5), // for 5 of the 60 seconds do nothing load test is only 55 seconds long
      atOnceUsers(10), // 10 users per sec plus
      rampUsers(20).during(30) // 1->20(11->30) users per second with a ramp of 30seconds
    ).protocols(httpProtocol)
  ).maxDuration(60)

}
