package ie.williamswalsh.feeders

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import sun.security.util.Length

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.concurrent.duration.DurationInt
import scala.util.Random

class ComplexCustomFeeder extends Simulation {

  var ids = (1 to 10).iterator
  var rnd = new Random()
  val today = LocalDate.now()
  var pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  val httpProtocol = http.baseUrl("https://videogamedb.uk/api")
    .acceptHeader("application/json")

  def getRandomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  def getRandomDate(startDate: LocalDate, random: Random): String = {
    startDate.minusDays(random.nextInt(29)).format(pattern)
  }

  def getRandomScore(random: Random): Int = {
    random.nextInt(100)
  }

  val customFeeder = Iterator.continually(Map(
    "gameId" -> ids.next(),
    "name" -> ("Game-" + getRandomString(6)),
    "releaseDate" -> getRandomDate(today, rnd),
    "reviewScore" -> rnd.nextInt(100),
    "category" -> ("Category-" + getRandomString(6)),
    "rating" -> ("Rating-" + getRandomString(4))
  ))

  def authenticate() = {
    exec(http("Authenticate")
      .post("/authenticate")
      .header("Content-Type", "application/json")
      .body(StringBody("{\n  \"password\": \"admin\",\n  \"username\": \"admin\"\n}"))
      .check(jsonPath("$.token").saveAs("jwtToken"))
    )
  }




// ElFileBody function takes a json template
// .asJson converts it into a json object
  def createNewGames() = {
    repeat(10) {
      feed(customFeeder)
        .exec(http("Create new game - #{name}")
        .post("/videogame")
        .header("authorization", "Bearer #{jwtToken}")
        .body(ElFileBody("bodies/newGameTemplate.json")).asJson
        .check(bodyString.saveAs("responseBody")))
        .exec {
          session => println(session("responseBody").as[String]); session
        }
        .pause(500.milliseconds)

    }
  }


  val scn = scenario("Auth and create 10 games")
    .exec(authenticate())
    .exec(createNewGames())


  setUp(
    scn.inject(atOnceUsers(1))
      .protocols(httpProtocol)
  )
}
