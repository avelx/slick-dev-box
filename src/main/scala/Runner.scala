package com.avelimited.slick.demo

//import slick.jdbc.H2Profile.api._
import slick.driver.{JdbcProfile, MySQLDriver}
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

object Runner {

  def main(args: Array[String]): Unit = {

    import data.Schema._
    import data.Db._

    val db = Database.forURL("jdbc:mysql://127.0.0.1:3306/slick",
      "root",
      "123123",
      driver = "com.mysql.jdbc.Driver")
    try {

      val setupFuture = db.run(setup)
      val res = Await.result(setupFuture, Duration.Inf)

      println(res)

      // Querying 1.
      //      println("Coffees:")
      //      db.run(coffees.result).map(_.foreach {
      //        case (name, supID, price, sales, total) =>
      //          println("  " + name + "\t" + supID + "\t" + price + "\t" + sales + "\t" + total)
      //      })

      val ins1: DBIO[Int] = coffees += ("ColombianData", 101, 17.99, 0, 0)
      val ins2: DBIO[Int] = coffees += ("French_RoastData", 101, 87.99, 0, 0)

      val a1: DBIO[Unit] = DBIO.seq(ins1, ins2)

      val a2: DBIO[Int] = ins1 andThen ins2

      val a3: DBIO[(Int, Int)] = ins1 zip ins2

      val a4: DBIO[Vector[Int]] = DBIO.sequence(Vector(ins1, ins2))

      val f: Future[_] = db.run(a3)

      f.onComplete {
        case Success(s) => println(s"Result: $s")
        case Failure(t) => t.printStackTrace()
      }

      Thread.sleep(1000)

      // Why not let the database do the string conversion and concatenation?
      val q1 = for{
        c <- coffees
        //if (c.price > 9.0)
      } yield LiteralColumn("  ") ++ c.name ++ "\t" ++ c.supID.asColumnOf[String] ++
          "\t" ++ c.price.asColumnOf[String] ++ "\t" ++ c.sales.asColumnOf[String] ++
          "\t" ++ c.total.asColumnOf[String]
      // The first string constant needs to be lifted manually to a LiteralColumn
      // so that the proper ++ operator is found

      // Equivalent SQL code:
      // select '  ' || COF_NAME || '\t' || SUP_ID || '\t' || PRICE || '\t' SALES || '\t' TOTAL from COFFEES



//      val source = db
//        .stream(q1.result)
//        .foreach(println)

      val q5 = coffees.filter(_.supID === 101)

      val q11 = coffees.filter(_.price < 8.0)
      val q12 = coffees.filter(_.price > 9.0)

      val unionQuery = q11 union q12

      val qWithGroupBy = (for {
        c <- coffees
        s <- c.supplier
      } yield (c, s)).groupBy(_._1.supID)

//      val q25 = qWithGroupBy.map { case (supID, css) =>
//        (supID, css)
//      }

      val source = db
              .stream(q1.result)
              .foreach(println)

      Thread.sleep(2000)
      //println(setupFuture)
    } finally db.close


  }

}
