package com.mtproto.utils

import akka.util.ByteString

import scala.util.Random

object Utils {

  private val random = new Random()

  def createRandomLong: Long = random.nextLong()

  def createRandomBigInt(length: Int): BigInt = BigInt.probablePrime(length, random)

  def createRandomByteString(length: Int): ByteString = ByteString(createRandomBytes(length))

  def createRandomBytes(length: Int): Array[Byte] = {
    val generatedArray = new Array[Byte](length)
    random.nextBytes(generatedArray)
    generatedArray
  }

}
