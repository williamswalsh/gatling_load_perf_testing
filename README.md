# Gatling Fundamentals for API Testing

```scala

# Run specific simulation in Maven:
FORMAT: mvn gatling:test -Dgatling.simulationClass=<SIMULATION_CLASS_PATH>
SAMPLE: mvn gatling:test -Dgatling.simulationClass=ie.williamswalsh.feeders.BasicCustomFeeder

# TODO
# Try to display data in response time across percentiles graph
```

```scala
// Demonstrate Open load model vs closed?
// constantUsersPerSec(10): Sends 10 new users per second, no matter how busy the system is.
// This avoids coordinated omission, as Gatling continues to inject users based on time, not response times.

//Open Load methods:
//==================
//constantUsersPerSec
//rampUsersPerSec
//heavisideUsers

setUp(
  scenario.inject(
    constantUsersPerSec(10).during(30.seconds)
  )
).protocols(httpProtocol)
```

```scala
// injections
// injections are not sequential they occur simultaneaously


// This will send a total of 500 users through the system over a period of 1800 seconds (or 30 minutes).
rampUsers(500) over (1800 seconds)



// for 50 of the 60 seconds do nothing load test is only 10 seconds long
  setUp(
    scn.inject(
      nothingFor(50), // this is not normal
      atOnceUsers(10),
      rampUsers(20).during(30)
    ).protocols(httpProtocol)
  ).maxDuration(60)

//
    setUp(
      scn.inject(
        nothingFor(5),
        constantUsersPerSec(10).during(10)
      ).protocols(httpProtocol)
    )

  // Fixed duration - 60 secs
  // 10 users -> 11 -> 30 -> remains at 30 until test ends
  setUp(
    scn.inject(
      nothingFor(5), // for 5 of the 60 seconds do nothing load test is only 55 seconds long
      atOnceUsers(10), // 10 users per sec plus
      rampUsers(20).during(30) // 1->20(11->30) users per second with a ramp of 30seconds
    ).protocols(httpProtocol)
  ).maxDuration(60)

```

```scala

# inferHtmlResources - Gatling will parse the HTML and automatically generate requests for resources like images, stylesheets, and scripts referenced in the page.
# BlackList: Excludes resources matching the specified patterns (e.g., ads or .png files).
# WhiteList: Includes only resources matching the specified patterns (e.g., .css and .js files).

val httpConf = http.baseUrl("https://example.com")
  .inferHtmlResources(
    BlackList(".*/ads/.*", ".*\\.png"),
    WhiteList(".*\\.css", ".*\\.js")
  )
```
