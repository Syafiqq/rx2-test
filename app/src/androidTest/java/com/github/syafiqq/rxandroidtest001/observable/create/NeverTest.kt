package com.github.syafiqq.rxandroidtest001.observable.create

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NeverTest {
    @Test
    fun it_successful_implement_never() {
        Observable.never<Int>()
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertNotComplete()
            .assertNoValues()
    }
}