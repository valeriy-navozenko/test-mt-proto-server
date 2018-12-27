package com.test.messages

import akka.util.ByteString

// See https://core.telegram.org/mtproto/description#unencrypted-message
case class UnencryptedMessage(authKeyId: Long, messageId: Long, messageDataLength: Int, messageData: ByteString)
