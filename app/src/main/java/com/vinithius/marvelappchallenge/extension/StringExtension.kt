package com.vinithius.marvelappchallenge.extension

import java.math.BigInteger
import java.security.MessageDigest

/**
 * Convert String to MD5 hash.
 */
fun String.hashMD5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(this.toByteArray())).toString(16).padStart(32, '0')
}
