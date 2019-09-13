package com.github.syafiqq.rxandroidtest001.transform

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Observable
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class BufferTest {

    @After
    fun tearDown() {
        Thread.sleep(100)
        System.gc()
        Thread.sleep(100)
    }

    @Test
    fun it_should_implement_buffer_with_count() {
        Observable.fromIterable(1..10)
            .buffer(3)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValues(listOf(1, 2, 3), listOf(4, 5, 6), listOf(7, 8, 9), listOf(10))
    }

    @Test
    fun it_should_implement_buffer_with_count_and_skip() {
        Observable.fromIterable(1..10)
            .buffer(3, 3)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValues(listOf(1, 2, 3), listOf(4, 5, 6), listOf(7, 8, 9), listOf(10))
    }

    @Test
    fun it_should_implement_buffer_with_count_skip_and_supplier() {
        Observable.fromIterable(1..10)
            .buffer(3, 3) { LinkedList<Int>() as MutableCollection<Int> }
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValues(
                mutableListOf(1, 2, 3),
                mutableListOf(4, 5, 6),
                mutableListOf(7, 8, 9),
                mutableListOf(10)
            )
    }

    @Test
    fun it_should_implement_buffer_with_count_and_supplier() {
        Observable.fromIterable(1..10)
            .buffer(3) { LinkedList<Int>() as MutableCollection<Int> }
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValues(
                mutableListOf(1, 2, 3),
                mutableListOf(4, 5, 6),
                mutableListOf(7, 8, 9),
                mutableListOf(10)
            )
    }

    @Test
    fun it_should_implement_buffer_with_timespan_and_timeskip() {
        Observable.interval(10, TimeUnit.MILLISECONDS)
            .buffer(31, 62, TimeUnit.MILLISECONDS)
            .test()
            .awaitDone(100, TimeUnit.MILLISECONDS)
            .assertSubscribed()
            .assertNoErrors()
            .assertNotComplete()
            .assertValues(listOf(0, 1, 2), listOf(6, 7, 8))
    }

    @Test
    fun it_should_implement_buffer_with_timespan() {
        Observable.interval(10, TimeUnit.MILLISECONDS)
            .buffer(31, TimeUnit.MILLISECONDS)
            .test()
            .awaitDone(100, TimeUnit.MILLISECONDS)
            .assertSubscribed()
            .assertNoErrors()
            .assertNotComplete()
            .assertValues(listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8))
    }

    @Test
    fun it_should_implement_buffer_with_open_close_indicator() {
        Observable.interval(10, TimeUnit.MILLISECONDS)
            .buffer(Callable { Observable.timer(32, TimeUnit.MILLISECONDS) })
            .test()
            .awaitDone(100, TimeUnit.MILLISECONDS)
            .assertSubscribed()
            .assertNoErrors()
            .assertNotComplete()
            .assertValues(listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8))
    }

    @Test
    fun it_should_implement_buffer_with_boundary() {
        Observable.interval(10, TimeUnit.MILLISECONDS)
            .buffer(Observable.intervalRange(1, 4, 35, 30, TimeUnit.MILLISECONDS))
            .test()
            .awaitDone(100, TimeUnit.MILLISECONDS)
            .assertSubscribed()
            .assertNoErrors()
            .assertNotComplete()
            .assertValues(listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8))
    }

    @Test
    fun it_should_implement_buffer_with_closing_boundary() {
        Observable.interval(10, TimeUnit.MILLISECONDS)
            .buffer(Callable { Observable.timer(32, TimeUnit.MILLISECONDS) })
            .test()
            .awaitDone(100, TimeUnit.MILLISECONDS)
            .assertSubscribed()
            .assertNoErrors()
            .assertNotComplete()
            .assertValues(listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8))
    }
}