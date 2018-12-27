package com.mtproto.codecs

import com.mtproto.messages.{ReqDHParamsMessage, ReqPqMessage, ResPqMessage, UnencryptedMessage}
import scodec.Codec
import scodec.codecs._

object MessageCodecs {

  val reqPqMessageCodec: Codec[ReqPqMessage] = {
    ("identifier" | constant(int32.encode(ReqPqMessage.classId).require)) ::
      ("nonce" | int64)
  }.dropUnits.as[ReqPqMessage]

  val resPqMessageCodec: Codec[ResPqMessage] = {
    ("identifier" | constant(int32.encode(ResPqMessage.classId).require)) ::
      ("nonce" | int64) ::
      ("server_nonce" | int64) ::
      ("pq" | bytesString) ::
      ("server_public_key_fingerprints" | vector(int64))
  }.dropUnits.as[ResPqMessage]

  val reqDHParamsCodec: Codec[ReqDHParamsMessage] = {
    ("identifier" | constant(int32.encode(ReqDHParamsMessage.classId).require)) ::
      ("nonce" | int64) ::
      ("server_nonce" | int64) ::
      ("p" | bytesString) ::
      ("q" | bytesString) ::
      ("public_key_fingerprint" | int64) ::
      ("encrypted_data" | bytesString)
  }.dropUnits.as[ReqDHParamsMessage]

  val unencryptedMessageCodec: Codec[UnencryptedMessage] = {
    ("auth_key_id" | int64) ::
      ("message_id" | int64) ::
      ("message_data_length" | int32) ::
      ("message_data" | bytesString)
  }.as[UnencryptedMessage]

}
