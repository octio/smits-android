package solutions.octio.smits.core.functional

suspend inline fun <T> resultFrom(crossinline block: suspend () -> T): Result<T> =
    catching { retryIO { block() } }