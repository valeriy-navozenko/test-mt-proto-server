package com.mtproto.messages

import akka.util.ByteString
import com.mtproto.codecs.MessageCodecs
import com.mtproto.security.rsa.RSAPublicKey

object PqInnerData {
  val classId: Int = 0x83c95aec

  def encrypt(innerData: PqInnerData, publicKey: RSAPublicKey): ByteString = {
    val encodedData = MessageCodecs.pqInnerDataCodec.encode(innerData).require
    ByteString(publicKey.encrypt(encodedData.toByteArray))
  }

}

case class PqInnerData(pq: ByteString, p: ByteString, q: ByteString, nonce: Long, serverNonce: Long, newNonce: Long)
