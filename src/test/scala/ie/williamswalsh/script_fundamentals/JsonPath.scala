package ie.williamswalsh.script_fundamentals

// Underscore is wildcard char
import io.gatling.core.Predef._
import io.gatling.http.Predef._

// Setting the duration to: 1000.milliseconds

class JsonPath extends Simulation {
  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  val scn = scenario("Check with JSON Path")
    .exec(
      http("Get specific game")
        .get("/videogame/5")
        .check(jsonPath("$.name").is("The Legend of Zelda: Ocarina of Time")))
    .exec(http("Get name from all videogames response")
        .get("/videogame")
        .check(jsonPath("$[4].id").saveAs("gameId")))
//    index 4 -> 5th element in array

    .exec(http("Get id from all videogames response")
      .get("/videogame")
      .check(jsonPath("$[4].id").saveAs("gameId"))
      .check(bodyString.saveAs("responseBody")))

//    Print responseBody user created variable
    .exec { session =>
      println(session("responseBody").as[String])
      session
    }

//    debug session obj
//    need to return session at end due to lambda structure
    .exec { session => println(session); session }



    .exec(http("Get specific from all videogames response")
      .get("/videogame/#{gameId}")
      .check(jsonPath("$.name").is("The Legend of Zelda: Ocarina of Time")))

  setUp(
    scn.inject(atOnceUsers(1))
      .protocols(httpProtocol)
  )
}
