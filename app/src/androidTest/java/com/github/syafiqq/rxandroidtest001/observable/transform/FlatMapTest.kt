package com.github.syafiqq.rxandroidtest001.observable.transform

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class FlatMapTest {
    @After
    fun tearDown() {
        Thread.sleep(100)
        System.gc()
        Thread.sleep(100)
    }

    @Test
    fun it_should_implement_flat_map() {
        val s0 = TestScheduler()
        val s1 = TestScheduler()
        val s = listOf(s0, s1)
        val o = Observable
            .fromIterable(1..3)
            .flatMap {
                Observable.intervalRange(
                    it.toLong(),
                    (it * 2).toLong(),
                    0,
                    10,
                    TimeUnit.MILLISECONDS,
                    s0
                )
            }
            .buffer(Observable.intervalRange(0, 6, 0, 10, TimeUnit.MILLISECONDS, s1))
            .test()

        o.assertSubscribed()
            .assertNoErrors()
            .assertNotComplete()

        s.forEach { it.advanceTimeBy(0, TimeUnit.MILLISECONDS) }
        o.assertNotComplete()
            .assertValues(listOf(1, 2, 3))

        s.forEach { it.advanceTimeBy(10, TimeUnit.MILLISECONDS) }
        o.assertNotComplete()
            .assertValues(listOf(1, 2, 3), listOf(2, 3, 4))

        s.forEach { it.advanceTimeBy(10, TimeUnit.MILLISECONDS) }
        o.assertNotComplete()
            .assertValues(listOf(1, 2, 3), listOf(2, 3, 4), listOf(4, 5))

        s.forEach { it.advanceTimeBy(10, TimeUnit.MILLISECONDS) }
        o.assertNotComplete()
            .assertValues(listOf(1, 2, 3), listOf(2, 3, 4), listOf(4, 5), listOf(5, 6))

        s.forEach { it.advanceTimeBy(10, TimeUnit.MILLISECONDS) }
        o.assertNotComplete()
            .assertValues(
                listOf(1, 2, 3),
                listOf(2, 3, 4),
                listOf(4, 5),
                listOf(5, 6),
                listOf(7)
            )

        s.forEach { it.advanceTimeBy(10, TimeUnit.MILLISECONDS) }
        o.assertComplete()
            .assertValues(
                listOf(1, 2, 3),
                listOf(2, 3, 4),
                listOf(4, 5),
                listOf(5, 6),
                listOf(7),
                listOf(8)
            )
    }
}