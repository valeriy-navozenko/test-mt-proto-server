package com.mtproto.client

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Source, Tcp}
import akka.util.ByteString
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

object TestClient extends App {

  implicit val system = ActorSystem("mt-proto-server-test", ConfigFactory.defaultReference())
  implicit val materializer = ActorMaterializer()

  val (host, port) = ("localhost", 46666)
  val connection = Tcp().outgoingConnection(host, port)

  val replParser =
    Flow[String].takeWhile(_ != "q")
      .concat(Source.single("BYE"))
      .map(elem => ByteString(s"$elem\n"))

  val repl = Flow[ByteString]
    .map(_.utf8String)
    .map(text => println("Server: " + text))
    .map(_ => StdIn readLine "> ")
    .via(replParser)

  connection.join(repl).run()
}
