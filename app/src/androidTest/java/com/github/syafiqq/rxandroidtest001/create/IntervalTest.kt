package com.github.syafiqq.rxandroidtest001.create

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class IntervalTest {
    @Test
    fun it_successful_implement_interval() {
        val o = Observable.interval(100, TimeUnit.MILLISECONDS).takeUntil { it < 5 }

        val subscriber = TestObserver<Long>()
        o.subscribe(subscriber)

        Thread.sleep(1000)

        subscriber.assertNoErrors()
        subscriber.assertComplete()
        subscriber.assertValue(1)
    }
}