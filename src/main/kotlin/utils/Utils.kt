package utils

import java.math.BigInteger
import java.security.MessageDigest

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')


fun expectResult(expected: Int, processor: () -> Int) {
    val actual = processor.invoke()
    if (actual != expected) {
        error("Expected $expected but received $actual")
    }
}

fun expectLongResult(expected: Long, processor: () -> Long) {
    val actual = processor.invoke()
    if (actual != expected) {
        error("Expected $expected but received $actual")
    }
}