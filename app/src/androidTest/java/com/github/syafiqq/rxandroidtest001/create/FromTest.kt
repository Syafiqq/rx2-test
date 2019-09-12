package com.github.syafiqq.rxandroidtest001.create

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
class FromTest {
    @Test
    fun it_successful_create_with_future() {
        val c = CompletableFuture.supplyAsync {
            1
        }
        Observable.fromFuture(c)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValue(1)
    }

    @Test
    fun it_successful_create_from_future_with_schedule() {
        val c = CompletableFuture.supplyAsync {
            1
        }
        Observable.fromFuture(c, Schedulers.trampoline())
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValue(1)
    }

    @Test
    fun it_successful_create_from_future_with_schedule_and_timeout() {
        val c = CompletableFuture.supplyAsync {
            Thread.sleep(100)
            1
        }
        Observable.fromFuture(c, 150L, TimeUnit.MILLISECONDS, Schedulers.trampoline())
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValue(1)
            .awaitDone(200L, TimeUnit.MILLISECONDS)
    }

    @Test
    fun it_successful_create_with_future_with_time_unit() {
        val c = CompletableFuture.supplyAsync {
            Thread.sleep(100)
            1
        }
        Observable.fromFuture(c, 150L, TimeUnit.MILLISECONDS)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValue(1)
            .awaitDone(200L, TimeUnit.MILLISECONDS)
        Thread.sleep(200)
    }

    @Test
    fun it_successful_create_with_future_with_surpass_time_unit() {
        val c = CompletableFuture.supplyAsync {
            Thread.sleep(100)
            1
        }
        Observable.fromFuture(c, 50L, TimeUnit.MILLISECONDS)
            .test()
            .assertSubscribed()
            .assertError(TimeoutException::class.java)
            .assertNotComplete()
            .assertNoValues()
            .awaitDone(200L, TimeUnit.MILLISECONDS)
    }

    @Test
    fun it_successful_create_from_iterable() {
        val c = 1..5
        Observable.fromIterable(c)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValues(1, 2, 3, 4, 5)
    }

    @Test
    fun it_successful_create_from_array() {
        Observable.fromArray(1, 2, 3, 4, 5)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValues(1, 2, 3, 4, 5)
    }

    @Test
    fun it_successful_create_from_callable() {
        val c = Callable {
            1
        }
        Observable.fromCallable(c)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValue(1)
    }
}