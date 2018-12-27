package com.mtproto.messages

import akka.util.ByteString

object ReqDHParamsMessage {
  val classId: Int = 0xd712e4be
}

// req_DH_params#d712e4be nonce:int128 server_nonce:int128 p:string q:string public_key_fingerprint:long encrypted_data:string = Server_DH_Params
case class ReqDHParamsMessage(nonce: Long, serverNonce: Long, p: ByteString, q: ByteString, fingerPrint: Long, encryptedData: ByteString)
