package ie.williamswalsh.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RampUsersLoadSimulation extends Simulation {
  val httpProtocol = http.baseUrl("http://videogamedb.uk/api")
    .acceptHeader("application/json")

  def getAllVideoGames = {
    exec(http("Get all games").get("/videogame"))
  }

  def getSpecificVideoGame = {
    exec(http("Get single game").get("/videogame/1"))
  }

  val scn = scenario("Basic load simulations")
    .exec(getAllVideoGames);
//    .pause(5)
//    .exec(getSpecificVideoGame())
//    .pause(5)
//    .exec(getAllVideoGames())

//  setUp(
//    scn.inject(
//      nothingFor(5),
//      constantUsersPerSec(10).during(10)
//    ).protocols(httpProtocol)
//  )

  setUp(
    scn.inject(
      constantUsersPerSec(10).during(10),
      rampUsersPerSec(1).to(5).during(20)
    ).protocols(httpProtocol)
  )

}
