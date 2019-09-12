package com.github.syafiqq.rxandroidtest001.create

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

@RunWith(AndroidJUnit4::class)
class RepeatTest {
    @Test
    fun it_should_repeat_5_2_times_implicitly() {
        Observable.just(5)
            .repeat()
            .take(2)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValues(5, 5)
    }

    @Test
    fun it_should_repeat_5_2_times_explicitly() {
        Observable.just(5)
            .repeat(2)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValues(5, 5)
    }

    @Test
    fun it_should_repeat_using_when() {
        Observable.just(5)
            .repeatWhen { it.delay(10, TimeUnit.MILLISECONDS) }
            .take(2)
            .test()
            .awaitDone(20, TimeUnit.MILLISECONDS)
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValues(5, 5)
    }

    @Test
    fun it_should_repeat_using_until() {
        val bool = AtomicBoolean(true)
        Observable.just(bool)
            .delay(200, TimeUnit.MILLISECONDS)
            .forEach { it.set(false) }
        Observable.just(5)
            .delay(100, TimeUnit.MILLISECONDS)
            .repeatUntil { !bool.get() }
            .test()
            .awaitDone(250, TimeUnit.MILLISECONDS)
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValueCount(2)
    }
}