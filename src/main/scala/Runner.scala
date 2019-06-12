package com.avelimited.slick.demo

import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object Runner {

  def main(args: Array[String]): Unit = {

    import data.Schema._
    import data.Db._

    val db = Database.forConfig("h2mem1")
    try {

      val setupFuture = db.run(setup)
      val res = Await.result(setupFuture, Duration.Inf)

      println(res)

      println("Coffees:")
      db.run(coffees.result).map(_.foreach {
        case (name, supID, price, sales, total) =>
          println("  " + name + "\t" + supID + "\t" + price + "\t" + sales + "\t" + total)
      })


      Thread.sleep(2000)
      //println(setupFuture)
    } finally db.close



  }

}
