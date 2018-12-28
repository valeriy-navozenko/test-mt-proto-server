package com.mtproto.messages

import akka.util.ByteString
import com.mtproto.utils.Utils._

// See https://core.telegram.org/mtproto/auth_key

object ResPqMessage {
  val classId: Int = 0x05162463

  def createRandom: ResPqMessage = ResPqMessage(
    createRandomLong,
    createRandomLong,
    createRandomByteString(32),
    Vector(createRandomLong)
  )
}

// resPQ#05162463 nonce:int128 server_nonce:int128 pq:string server_public_key_fingerprints:Vector long = ResPQ;
case class ResPqMessage(nonce: Long, serverNonce: Long, pq: ByteString, fingerPrints: Vector[Long])
