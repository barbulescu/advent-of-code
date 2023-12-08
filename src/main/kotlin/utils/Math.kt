package utils

fun leastCommonMultiple(a: Long, b: Long): Long =
    a * b / greatestCommonDivisor(a, b)

tailrec fun greatestCommonDivisor(a: Long, b: Long): Long =
    if (b == 0L)
        a
    else
        greatestCommonDivisor(b, a % b)