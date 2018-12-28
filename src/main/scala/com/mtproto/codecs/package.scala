package com.mtproto

import akka.util.ByteString
import com.mtproto.messages.{Message, ReqDHParamsMessage, ReqPqMessage, ResPqMessage}
import scodec.Attempt.Failure
import scodec.bits.ByteVector
import scodec.codecs._
import scodec.{Codec, Err}

package object codecs {

  def bytesString: Codec[ByteString] = bytes.asByteString

  implicit class RichCodecByteVector(val codec: Codec[ByteVector]) extends AnyVal {
    def asByteString: Codec[ByteString] = codec
      .xmap[ByteString](byteVector => ByteString(byteVector.toArray), byteString => ByteVector(byteString))
  }

  def message: Codec[Message] = variableSizeBytes(int32, bytes).exmap[Message](data => {
    def decode[T <: Message](codec: Codec[T]) = codec.decode(data.bits).map(_.value)

    int32.decode(data.bits).flatMap {
      case scodec.DecodeResult(value, _) => value match {
        case ReqPqMessage.classId => decode(MessageCodecs.reqPqMessageCodec)
        case ReqDHParamsMessage.classId => decode(MessageCodecs.reqDHParamsMessageCodec)
        case classId => Failure(Err(s"Can't deserialize message $classId"))
      }
    }
  }, {
    case reqPqMessage: ReqPqMessage => MessageCodecs.reqPqMessageCodec.encode(reqPqMessage).map(_.bytes)
    case reqDHParamsMessage: ReqDHParamsMessage => MessageCodecs.reqDHParamsMessageCodec.encode(reqDHParamsMessage).map(_.bytes)
    case resPqMessage: ResPqMessage => MessageCodecs.resPqMessageCodec.encode(resPqMessage).map(_.bytes)
    case message => Failure(Err(s"Can't serialize message $message"))
  })

}
