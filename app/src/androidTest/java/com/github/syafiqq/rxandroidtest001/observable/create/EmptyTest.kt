package com.github.syafiqq.rxandroidtest001.observable.create

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmptyTest {
    @Test
    fun it_successful_access_next_and_complete() {
        Observable.empty<Int>()
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertNoValues()
    }
}