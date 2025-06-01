package ie.williamswalsh.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class BasicLoadSimulation extends Simulation {
  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  def getAllVideoGames() = {
    exec(http("Get all games").get("/videogame"))
  }

  def getSpecificVideoGame() = {
    exec(http("Get single game").get("/videogame/1"))
  }

  val scn = scenario("Basic load simulations")
    .exec(getAllVideoGames())
    .pause(5)
    .exec(getSpecificVideoGame())
    .pause(5)
    .exec(getAllVideoGames())

  setUp(
    scn.inject(
      nothingFor(5), // do nothing for 5 seconds
      atOnceUsers(5), // Do 5 users per second
      rampUsers(10).during(10)
    )
      .protocols(httpProtocol)
  )
}
