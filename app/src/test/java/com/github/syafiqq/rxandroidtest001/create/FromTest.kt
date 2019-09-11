package com.github.syafiqq.rxandroidtest001.create

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test
import java.util.concurrent.CompletableFuture

class FromTest {
    @Test
    fun it_successful_create_with_future() {
        val s = CompletableFuture.completedFuture(1)
        val o = Observable.fromFuture(s)

        val subscriber = TestObserver<Int>()
        o.subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertComplete()
        subscriber.assertValue(1)
    }
}