/*
  Download Gatling from https://gatling.io and unpack it somewhere.
  The directory with the bin, conf, user-files, etc., directories will be your Gatling root.

  This simulation file needs to be copied into a directory named "workshop"
  inside the "<gatling-root>/user-files/simulations" directory.

  Then run Gatlin using the appropriate script in the <gatling-root>/bin directory.
 */

package workshop

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080") // Here is the root for all relative URLs
    .acceptHeader("application/json") // Here are the common headers

  val scn = scenario("Names") // A scenario is a chain of requests and pauses
    .exec(
      http("request_1")
        .get("/names")
    )

  setUp(
    scn.inject(
      rampUsers(500).during(10.seconds),
      constantUsersPerSec(500).during(30.seconds)
    ).protocols(httpProtocol))
}
