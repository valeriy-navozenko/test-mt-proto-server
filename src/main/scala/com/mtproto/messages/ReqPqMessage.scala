package com.mtproto.messages

// See https://core.telegram.org/mtproto/auth_key

object ReqPqMessage {
  val classId: Int = 0x60469778
}

// req_pq#60469778 nonce:int128 = ResPQ;
case class ReqPqMessage(nonce: Long)
