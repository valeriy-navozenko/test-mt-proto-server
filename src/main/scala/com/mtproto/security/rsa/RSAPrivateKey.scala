package com.mtproto.security.rsa

import java.security.interfaces.{RSAPrivateKey => JavaRSAPrivateKey}

import javax.crypto.Cipher

class RSAPrivateKey(privateKey: JavaRSAPrivateKey) {

  def decrypt(encrypted: Array[Byte]): Array[Byte] = {
    rsaECBCipher.init(Cipher.DECRYPT_MODE, privateKey)
    rsaECBCipher.doFinal(encrypted)
  }

}
