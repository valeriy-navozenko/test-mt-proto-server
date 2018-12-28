package com.mtproto.server

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Tcp}
import akka.util.ByteString
import com.mtproto.codecs.MessageCodecs
import com.mtproto.messages.UnencryptedMessage
import com.typesafe.config.ConfigFactory
import scodec.bits.BitVector

object MTProtoServer extends App {

  implicit val system = ActorSystem("mt-proto-server-test", ConfigFactory.defaultReference())
  implicit val materializer = ActorMaterializer()

  val (host, port) = ("localhost", 46666)

  Tcp()
    .bind(host, port)
    .runForeach { connection =>
      val commandParser = Flow[String].takeWhile(_ != "BYE").map(_ + "!")

      // TODO: Note that MTProto 2.0 requires from 12 to 1024 bytes of padding
      val serverLogic = Flow[ByteString]
        .map(message => MessageCodecs.unencryptedMessageCodec.decode(BitVector(message)).require.value)
        .via(new FlowProcess())
        .map(message => ByteString(
          MessageCodecs
            .unencryptedMessageCodec
            .encode(UnencryptedMessage(0, 0, message.messageDataLength, message.messageData)).require.bytes.toArray)
        )

      connection.handleWith(serverLogic)
    }
}
