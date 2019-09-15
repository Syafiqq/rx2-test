package com.github.syafiqq.rxandroidtest001.observable.filter

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class ThrottleTest {
    @After
    fun tearDown() {
        Thread.sleep(100)
        System.gc()
        Thread.sleep(100)
    }

    @Test
    fun it_should_throttle_with_strategy_throttle_last() {
        val t = TestScheduler()
        val s = PublishSubject.create<Int>()
        val o = s.throttleLast(10, TimeUnit.MILLISECONDS, t)
            .test()

        o.assertSubscribed()
            .assertNoErrors()
            .assertNotComplete()
            .assertNoValues()

        s.onNext(0)


        o.assertNoErrors()
            .assertNotComplete()
            .assertNoValues()

        t.advanceTimeBy(10, TimeUnit.MILLISECONDS)
        o.assertNoErrors()
            .assertNotComplete()
            .assertValues(0)

        (1..4).forEach(s::onNext)

        t.advanceTimeBy(10, TimeUnit.MILLISECONDS)
        o.assertNoErrors()
            .assertNotComplete()
            .assertValues(0, 4)

        s.onNext(1)
        t.advanceTimeBy(5, TimeUnit.MILLISECONDS)
        o.assertNoErrors()
            .assertNotComplete()
            .assertValues(0, 4)

        s.onNext(2)
        t.advanceTimeBy(5, TimeUnit.MILLISECONDS)
        o.assertNoErrors()
            .assertNotComplete()
            .assertValues(0, 4, 2)

        s.onNext(3)
        t.advanceTimeBy(5, TimeUnit.MILLISECONDS)
        o.assertNoErrors()
            .assertNotComplete()
            .assertValues(0, 4, 2)

        s.onNext(4)
        t.advanceTimeBy(10, TimeUnit.MILLISECONDS)
        o.assertNoErrors()
            .assertNotComplete()
            .assertValues(0, 4, 2, 4)
    }

    @Test
    fun it_should_throttle_with_strategy_throttle_first() {
        val t = TestScheduler()
        val s = PublishSubject.create<Int>()
        val o = s.throttleFirst(10, TimeUnit.MILLISECONDS, t)
            .test()

        o.assertSubscribed()
            .assertNoErrors()
            .assertNotComplete()
            .assertNoValues()

        s.onNext(0)

        o.assertNoErrors()
            .assertNotComplete()
            .assertValues(0)

        t.advanceTimeBy(10, TimeUnit.MILLISECONDS)
        o.assertNoErrors()
            .assertNotComplete()
            .assertValues(0)

        (1..4).forEach(s::onNext)

        t.advanceTimeBy(10, TimeUnit.MILLISECONDS)
        o.assertNoErrors()
            .assertNotComplete()
            .assertValues(0, 1)

        s.onNext(1)
        t.advanceTimeBy(5, TimeUnit.MILLISECONDS)
        o.assertNoErrors()
            .assertNotComplete()
            .assertValues(0, 1, 1)

        s.onNext(2)
        t.advanceTimeBy(5, TimeUnit.MILLISECONDS)
        o.assertNoErrors()
            .assertNotComplete()
            .assertValues(0, 1, 1)

        s.onNext(3)
        t.advanceTimeBy(5, TimeUnit.MILLISECONDS)
        o.assertNoErrors()
            .assertNotComplete()
            .assertValues(0, 1, 1, 3)

        s.onNext(4)
        t.advanceTimeBy(10, TimeUnit.MILLISECONDS)
        o.assertNoErrors()
            .assertNotComplete()
            .assertValues(0, 1, 1, 3)
    }
}