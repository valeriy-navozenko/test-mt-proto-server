package com.mtproto.security

import java.security.KeyFactory

import javax.crypto.Cipher

package object rsa {

  private val rsaECBCipher = Cipher.getInstance("RSA/ECB/NoPadding")
  private val rsaKeyFactory: KeyFactory = KeyFactory.getInstance("RSA")

}
