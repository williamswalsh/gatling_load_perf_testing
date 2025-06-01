error id: file://<WORKSPACE>/src/test/scala/ie/williamswalsh/simulations/BasicLoadSimulation.scala:`<none>`.
file://<WORKSPACE>/src/test/scala/ie/williamswalsh/simulations/BasicLoadSimulation.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -io/gatling/core/Predef.scn.inject.
	 -io/gatling/core/Predef.scn.inject#
	 -io/gatling/core/Predef.scn.inject().
	 -io/gatling/http/Predef.scn.inject.
	 -io/gatling/http/Predef.scn.inject#
	 -io/gatling/http/Predef.scn.inject().
	 -scn/inject.
	 -scn/inject#
	 -scn/inject().
	 -scala/Predef.scn.inject.
	 -scala/Predef.scn.inject#
	 -scala/Predef.scn.inject().
offset: 620
uri: file://<WORKSPACE>/src/test/scala/ie/williamswalsh/simulations/BasicLoadSimulation.scala
text:
```scala
package ie.williamswalsh.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

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
    scn.inje@@ct(
      nothingFor(5), // do nothing for 5 seconds
      atOnceUsers(5), // Do 5 users per second
      rampUsers(10).during(10)
    )
      .protocols(httpProtocol)
  )
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.