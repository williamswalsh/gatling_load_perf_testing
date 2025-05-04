package ie.williamswalsh.script_fundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class CodeReuse extends Simulation{

  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  def getAllVideoGamesPlain() = {
    exec(http("Get all videogames")
      .get("/videogame"))
  }

//  Method definition
  def getAllVideoGames(williamsCounter: String) = {
    exec(http("Get all videogames count: #{williamsCounter}")
      .get("/videogame"))
  }

  //  Repeat API call N times
  def getAllVideoGamesNTimes() = {
    repeat(5 ) {
      exec(http("Get all videogames")
        .get("/videogame"))
    }
  }

  //  Repeat API call N times
  def getAllVideoGamesNTimesWithCounterPrintOut() = {
    repeat(5, "williamsCounter") {
      exec(http("Get all videogames #{williamsCounter}")
        .get("/videogame"))
    }
  }

  def getSpecificVideoGameWithCounter() = {
    repeat(5, "gameId") {
      exec(http("Get specific videogame with gameId: #{gameId}")
        .get("/videogame/#{gameId}"))
    }
  }


  def getSpecificVideoGame() = {
    exec(http("Get specific videogame")
      .get("/videogame/1"))
  }

//  def getSpecificVideoGame(gameId : String) = {
//    exec(http("Get specific videogame")
//      .get("/videogame/#{gameId}"))
//  }

  val scn = scenario("Code reuse")
    .exec(getSpecificVideoGameWithCounter())
    .pause(100.milliseconds)
    .exec(getSpecificVideoGame())
    .pause(100.milliseconds)

// Repeat calling of method
//    Passing counter string value into method call
    .repeat(2, "williamsCounter") {
      getAllVideoGames("#{williamsCounter}")
    }

  setUp(
    scn.inject(atOnceUsers(1))
      .protocols(httpProtocol)
  )

}
