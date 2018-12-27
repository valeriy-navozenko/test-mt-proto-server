package com.test.messages

import akka.util.ByteString

// See https://core.telegram.org/mtproto/auth_key

object ResPqMessage {
  val classId: Int = 0x05162463
}

// resPQ#05162463 nonce:int128 server_nonce:int128 pq:string server_public_key_fingerprints:Vector long = ResPQ;
case class ResPqMessage(nonce: BigInt, serverNonce: BigInt, pq: ByteString, fingerPrints: Vector[Long])
