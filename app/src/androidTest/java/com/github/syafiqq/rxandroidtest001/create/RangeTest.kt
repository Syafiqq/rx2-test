package com.github.syafiqq.rxandroidtest001.create

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class RangeTest {
    @Test
    fun it_should_emit_1_to_5() {
        Observable.range(1, 5)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValues(1, 2, 3, 4, 5)
    }

    @Test
    fun it_should_emit_1_to_5_with_interval() {
        Observable.intervalRange(1, 5, 0, 10L, TimeUnit.MILLISECONDS)
            .test()
            .assertSubscribed()
            .awaitDone(100L, TimeUnit.MILLISECONDS)
            .assertNoErrors()
            .assertComplete()
            .assertValues(1, 2, 3, 4, 5)
    }

    @Test
    fun it_should_not_emit_1_to_5_with_interval_but_timeout() {
        Observable.intervalRange(1, 5, 0, 10L, TimeUnit.MILLISECONDS)
            .test()
            .assertSubscribed()
            .awaitDone(15L, TimeUnit.MILLISECONDS)
            .assertNoErrors()
            .assertNotComplete()
            .assertValues(1, 2)
    }
}