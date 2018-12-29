package com.mtproto.security

import java.security.KeyFactory

import javax.crypto.Cipher

package object rsa {

  val rsaECBCipher = Cipher.getInstance("RSA/ECB/NoPadding")
  val rsaKeyFactory: KeyFactory = KeyFactory.getInstance("RSA")

}
