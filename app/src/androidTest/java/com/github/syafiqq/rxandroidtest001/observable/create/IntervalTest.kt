package com.github.syafiqq.rxandroidtest001.observable.create

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class IntervalTest {
    @Test
    fun it_successful_implement_interval() {
        Observable.interval(50, TimeUnit.MILLISECONDS)
            .test()
            .assertSubscribed()
            .awaitDone(120L, TimeUnit.MILLISECONDS)
            .assertNoErrors()
            .assertNotComplete()
            .assertValues(0, 1)
    }

    @Test
    fun it_successful_implement_interval_with_two_step() {
        Observable.interval(50, TimeUnit.MILLISECONDS)
            .take(2)
            .test()
            .assertSubscribed()
            .awaitDone(120L, TimeUnit.MILLISECONDS)
            .assertNoErrors()
            .assertComplete()
            .assertValues(0, 1)
    }

    @Test
    fun it_successful_implement_interval_with_two_step_get_one() {
        Observable.interval(50, TimeUnit.MILLISECONDS)
            .take(2)
            .test()
            .assertSubscribed()
            .awaitDone(70L, TimeUnit.MILLISECONDS)
            .assertNoErrors()
            .assertNotComplete()
            .assertValues(0)
    }

    @Test
    fun it_successful_implement_interval_with_two_step_and_immediate_scheduler() {
        Observable.interval(50, TimeUnit.MILLISECONDS, Schedulers.computation())
            .take(2)
            .test()
            .assertSubscribed()
            .awaitDone(120L, TimeUnit.MILLISECONDS)
            .assertNoErrors()
            .assertComplete()
            .assertValues(0, 1)
    }

    @Test
    fun it_successful_implement_delay_interval() {
        Observable.interval(100, 50, TimeUnit.MILLISECONDS)
            .test()
            .assertSubscribed()
            .awaitDone(170L, TimeUnit.MILLISECONDS)
            .assertNoErrors()
            .assertNotComplete()
            .assertValues(0, 1)
    }

    @Test
    fun it_successful_implement_delay_interval_with_no_wait() {
        Observable.interval(100, 50, TimeUnit.MILLISECONDS)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertNotComplete()
            .assertNoValues()
    }

    @Test
    fun it_successful_implement_delay_interval_with_two_step() {
        Observable.interval(100, 50, TimeUnit.MILLISECONDS)
            .take(2)
            .test()
            .assertSubscribed()
            .awaitDone(220L, TimeUnit.MILLISECONDS)
            .assertNoErrors()
            .assertComplete()
            .assertValues(0, 1)
    }

    @Test
    fun it_successful_implement_delay_interval_with_two_step_and_immediate_scheduler() {
        Observable.interval(50, TimeUnit.MILLISECONDS, Schedulers.computation())
            .take(2)
            .test()
            .assertSubscribed()
            .awaitDone(220L, TimeUnit.MILLISECONDS)
            .assertNoErrors()
            .assertComplete()
            .assertValues(0, 1)
    }
}