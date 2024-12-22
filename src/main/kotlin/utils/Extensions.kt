package utils

fun List<Int>.middle() = this[size / 2]

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

fun StringBuilder.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

fun <T> List<T>.binarySearchFirst(selector: (T) -> Boolean): T =
    binarySearchFirstOrNull(selector) ?: throw IllegalStateException("No elements match predicate")

fun <T> List<T>.binarySearchFirstOrNull(selector: (T) -> Boolean): T? {
    val index = binarySearchIndexOfFirstOrNull(selector)
    return if (index == null) {
        null
    } else {
        get(index)
    }
}

fun <T> List<T>.binarySearchIndexOfFirstOrNull(fn: (T) -> Boolean): Int? {
    var low = 0
    var high = lastIndex
    var firstResult: Int? = null
    while (low <= high) {
        val mid = (low + high) / 2
        if (fn(get(mid))) {
            firstResult = mid
            high = mid - 1
        } else {
            low = mid + 1
        }
    }
    return firstResult
}
