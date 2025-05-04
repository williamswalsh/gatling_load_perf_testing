package ie.williamswalsh

// Underscore is wildcard char
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class MyFirstTest extends Simulation{
//  1) http protocol
  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

//  2) scenario defn
  val scn = scenario("My first test")
    .exec(http("Get all games")
    .get("/videogame"))


// 3) Load scenario
  setUp(
    scn.inject(atOnceUsers(1))
      .protocols(httpProtocol)
  )
}
