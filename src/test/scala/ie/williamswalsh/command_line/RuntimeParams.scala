package ie.williamswalsh.command_line

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RuntimeParams extends Simulation {
  val httpProtocol = http.baseUrl("http://videogamedb.uk/api")
    .acceptHeader("application/json")

  def getAllVideoGames = {
    exec(http("Get all games").get("/videogame"))
  }

  val scn = scenario("Basic load simulations")
    .forever(
      exec(getAllVideoGames)
    ).pause(1)  // Adding a pause to not overload the api - users will keep requesting resource

  setUp(
    scn.inject(
//      nothingFor(5),
      rampUsers(10).during(1)
    ).protocols(httpProtocol)
  ).maxDuration(20)
}
