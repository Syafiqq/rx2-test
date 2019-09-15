package com.github.syafiqq.rxandroidtest001.observable.create

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.syafiqq.rxandroidtest001.exception.CustomException
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ThrowTest {
    @Test
    fun it_successful_implement_throw() {
        Observable.error<Int>(CustomException())
            .test()
            .assertSubscribed()
            .assertError(CustomException::class.java)
            .assertNotComplete()
            .assertNoValues()
    }
}