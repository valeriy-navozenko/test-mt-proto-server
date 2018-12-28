package com.mtproto

package object messages {

  sealed trait Message

  trait RequestMessage extends Message

  trait ResponseMessage extends Message

}
