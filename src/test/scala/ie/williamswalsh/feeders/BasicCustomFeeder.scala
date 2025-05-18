package ie.williamswalsh.feeders

import io.gatling.http.Predef._
import io.gatling.core.Predef._

import scala.concurrent.duration.DurationInt

class BasicCustomFeeder extends Simulation {

  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json");

  val maxIterations = 10

  var idNumbers = (1 to maxIterations).iterator

  // give the "gameId" key the value from 1->100 or as many repeats it perform
//  repeat times value influences the max value returned from the custom feeder
  val customFeeder = Iterator.continually(Map("gameId" -> idNumbers.next()))

//  https://videogamedb.uk/api/videogame has 10 videoGames to retrieve. Games with an id gt >10 fail.

//  Transaction
  def getSpecificVideoGame() = {
    repeat(maxIterations) {
      feed(customFeeder)
        .exec(http("Get game by id with id #{gameId}")
          .get("/videogame/#{gameId}")
          .check(status.is(200))
        ).pause(500.milliseconds)
        .exec { session =>
//          val iteration = session("counter").as[Int]
          val myValue = session("gameId").asOption[String]
          println(s"myKey: $myValue")
          session
        }
    }
  }

  val scn = scenario("Basic custom feeder")
    .exec(getSpecificVideoGame())

  setUp(scn.inject(atOnceUsers(1)))
    .protocols(httpProtocol)
}
