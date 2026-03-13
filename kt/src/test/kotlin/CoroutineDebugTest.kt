import kotlinx.coroutines.*
import org.junit.Test

/**
 *    author : xxd
 *    date   : 2026/3/13
 *    desc   : 
 */
class CoroutineDebugTest {

    @Test
    fun testDebugView() = runBlocking {
        println("Starting Coroutines Debug Demo...")

        val job1 = launch(CoroutineName("Task-Alpha")) {
            delay(1000)
            println("Alpha is suspended...")
            doSomethingComplex() // 在这里打断点 A
        }

        val job2 = launch(CoroutineName("Task-Beta")) {
            delay(500)
            println("Beta is suspended...")
            yield() // 在这里打断点 B
        }

        joinAll(job1, job2)
    }

    private suspend fun doSomethingComplex() {
        delay(2000) // 这里可以看到挂起的调用栈
        println("Complex task done")
    }
}