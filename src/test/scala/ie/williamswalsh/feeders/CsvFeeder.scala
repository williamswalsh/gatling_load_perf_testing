package ie.williamswalsh.feeders

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt


class CsvFeeder extends Simulation {

  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json");

  val csvFeeder = csv("data/game.csv").circular

  def getSpecificVideoGame() = {

//    Repeat block
    repeat(10) {
//      Get data from feeder
      feed(csvFeeder)
        .exec(http("Get videogame by ID, with name - #{gameName}")
          .get("/videogame/#{gameId}")
          .check(jsonPath("$.name").is("#{gameName}"))
        .check(status.is(200)))
        .pause(500.milliseconds)

    }
  }

  val scn = scenario("My csv feeder scenario")
  .exec(getSpecificVideoGame);

  setUp(
    scn.inject(atOnceUsers(1)).protocols(httpProtocol)
  )
}
