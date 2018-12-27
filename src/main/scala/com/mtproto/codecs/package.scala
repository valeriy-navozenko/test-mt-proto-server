package com.mtproto

import akka.util.ByteString
import scodec.Codec
import scodec.bits.ByteVector
import scodec.codecs._

package object codecs {

  implicit class RichCodecByteVector(val codec: Codec[ByteVector]) extends AnyVal {
    def asByteString: Codec[ByteString] = codec
      .xmap[ByteString](byteVector => ByteString(byteVector.toArray), byteString => ByteVector(byteString))
  }

  def bytesString: Codec[ByteString] = bytes.asByteString

}
