package com.github.syafiqq.rxandroidtest001.create

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
class FromTest {
    @Test
    fun it_successful_create_with_future() {
        val s = CompletableFuture.supplyAsync {
            1
        }
        val o = Observable.fromFuture(s)

        val subscriber = TestObserver<Int>()
        o.subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertComplete()
        subscriber.assertValue(1)
    }

    @Test
    fun it_successful_create_with_future_with_time_unit() {
        val s = CompletableFuture.supplyAsync {
            Thread.sleep(100)
            1
        }
        val o = Observable.fromFuture(s, 150L, TimeUnit.MILLISECONDS)

        val subscriber = TestObserver<Int>()
        o.subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertComplete()
        subscriber.assertValue(1)
    }

    @Test
    fun it_successful_create_with_future_with_surpass_time_unit() {
        val s = CompletableFuture.supplyAsync {
            Thread.sleep(100)
            1
        }
        val o = Observable.fromFuture(s, 50L, TimeUnit.MILLISECONDS)

        val subscriber = TestObserver<Int>()
        o.subscribe(subscriber)

        subscriber.assertError(TimeoutException::class.java)
        subscriber.assertNotComplete()
        subscriber.assertNoValues()
    }
}