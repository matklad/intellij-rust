package org.rust.ide

object Perf {
    val resolve = Metric()


    inline fun measure(block: () -> Unit) {
        reset()
        val start = System.nanoTime()
        block()
        val end = System.nanoTime()
        report(end - start)
    }

    fun reset() {
        resolve.total = 0
    }

    fun report(totalNs: Long) {
        val nsPerMs = 1000 * 1000
        println("total:   ${totalNs / nsPerMs}ms")
        println("resolve: ${resolve.total / nsPerMs}ms")
    }

    class Metric(
        var total: Long = 0
    ) {
        inline fun <T> measure(block: () -> T): T {
            val start = System.nanoTime()
            val result = block()
            val end = System.nanoTime()
            total += end - start
            return result
        }
    }
}
