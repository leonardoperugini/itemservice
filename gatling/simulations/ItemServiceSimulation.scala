package itemservice

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class ItemServiceSimulation extends Simulation {
  val httpProtocol = http
    .baseUrl("http://localhost:8080") // Modifica se necessario
    .acceptHeader("application/json")

  val scn = scenario("Get all items")
    .exec(
      http("Get Items")
        .get("/api/items") // Modifica con il tuo endpoint
        .check(status.is(200))
    )

  setUp(
    scn.inject(rampUsers(10) during (10.seconds))
  ).protocols(httpProtocol)
}
