package ie.williamswalsh.script_fundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Authenticate extends Simulation {
  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")


  def authenticate() = {
    exec(http("Authenticate to auth api")
      .post("/authenticate")
      .header("Content-Type", "application/json")
      .body(StringBody("{\n  \"password\": \"admin\",\n  \"username\": \"admin\"\n}"))
      .check(jsonPath("$.token").saveAs("jwtToken"))
    )
  }

  def createNewGame() = {
    exec(http("Create new game")
      .post("/videogame")
      .check(status.is(200))
      .header("Authorization", "Bearer #{jwtToken}")
      .header("Content-Type", "application/json")
      .body(StringBody("{\n  \"category\": \"Platform\",\n  \"name\": \"Mario\",\n  \"rating\": \"Mature\",\n  \"releaseDate\": \"2012-05-04\",\n  \"reviewScore\": 85\n}")
    ))
  }

  val scn = scenario("Auth")
    .exec(authenticate())
    .exec(createNewGame())

  setUp(
    scn.inject(atOnceUsers(1))
      .protocols(httpProtocol)
  )
}
