package com.github.syafiqq.rxandroidtest001.create

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.syafiqq.rxandroidtest001.exception.CustomException
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ThrowTest {
    @Test
    fun it_successful_implement_throw() {
        val o = Observable.error<Int>(CustomException())

        val subscriber = TestObserver<Int>()
        o.subscribe(subscriber)

        subscriber.assertError(CustomException::class.java)
        subscriber.assertNotComplete()
        subscriber.assertNoValues()
    }
}