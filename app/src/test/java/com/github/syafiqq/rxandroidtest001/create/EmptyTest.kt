package com.github.syafiqq.rxandroidtest001.create

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test

class EmptyTest {
    @Test
    fun it_successful_access_next_and_complete() {
        val o = Observable.empty<Int>()

        val subscriber = TestObserver<Int>()
        o.subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertComplete()
        subscriber.assertNoValues()
    }
}