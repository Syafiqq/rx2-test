package com.github.syafiqq.rxandroidtest001.create

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class TimerTest {
    @Test
    fun it_should_emit_after_100ms() {
        Observable.timer(100L, TimeUnit.MILLISECONDS)
            .test()
            .assertSubscribed()
            .awaitDone(150L, TimeUnit.MILLISECONDS)
            .assertNoErrors()
            .assertComplete()
            .assertValue(0L)
    }

    @Test
    fun it_should_emit_after_100ms_with_scheduler() {
        Observable.timer(100L, TimeUnit.MILLISECONDS, Schedulers.trampoline())
            .test()
            .assertSubscribed()
            .awaitDone(150L, TimeUnit.MILLISECONDS)
            .assertNoErrors()
            .assertComplete()
            .assertValue(0L)
    }

    @Test
    fun it_should_not_emit_after_immediately() {
        Observable.timer(100L, TimeUnit.MILLISECONDS)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertNotComplete()
            .assertNoValues()
    }
}