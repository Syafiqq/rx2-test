package com.github.syafiqq.rxandroidtest001.create

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class IntervalTest {
    @Test
    fun it_successful_implement_interval() {
        val o = Observable.interval(50, TimeUnit.MILLISECONDS)

        val subscriber = TestObserver<Long>()
        o.subscribe(subscriber)

        Thread.sleep(120)

        subscriber.assertNoErrors()
        subscriber.assertNotComplete()
    }

    @Test
    fun it_successful_implement_interval_with_two_step() {
        val o = Observable.interval(50, TimeUnit.MILLISECONDS)
            .take(2)

        val subscriber = TestObserver<Long>()
        o.subscribe(subscriber)

        Thread.sleep(120)

        subscriber.assertNoErrors()
        subscriber.assertComplete()
        subscriber.assertValues(0, 1)
    }

    @Test
    fun it_successful_implement_interval_with_two_step_and_immediate_scheduler() {
        val o = Observable.interval(50, TimeUnit.MILLISECONDS, Schedulers.computation())
            .take(2)

        val subscriber = TestObserver<Long>()
        o.subscribe(subscriber)

        Thread.sleep(120)

        subscriber.assertNoErrors()
        subscriber.assertComplete()
        subscriber.assertValues(0, 1)
    }
}