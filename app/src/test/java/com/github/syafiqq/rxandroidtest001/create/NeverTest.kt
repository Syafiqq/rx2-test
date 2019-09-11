package com.github.syafiqq.rxandroidtest001.create

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test

class NeverTest {
    @Test
    fun it_successful_implement_never() {
        val o = Observable.never<Int>()

        val subscriber = TestObserver<Int>()
        o.subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertNotComplete()
        subscriber.assertNoValues()
    }
}