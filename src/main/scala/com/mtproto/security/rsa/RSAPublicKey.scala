package com.mtproto.security.rsa

import java.security.interfaces.{RSAPublicKey => JavaRSAPublicKey}
import java.security.spec.RSAPublicKeySpec

import javax.crypto.Cipher

class RSAPublicKey(modulus: BigInt, exponent: BigInt) {

  def generate(): JavaRSAPublicKey = {
    rsaKeyFactory.generatePublic(new RSAPublicKeySpec(modulus.bigInteger, exponent.bigInteger)).asInstanceOf[JavaRSAPublicKey]
  }

  def encrypt(message: Array[Byte]): Array[Byte] = {
    rsaECBCipher.init(Cipher.ENCRYPT_MODE, generate())
    rsaECBCipher.doFinal(message)
  }

}
