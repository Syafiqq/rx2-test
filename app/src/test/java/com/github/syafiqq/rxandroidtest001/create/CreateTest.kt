package com.github.syafiqq.rxandroidtest001.create

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test

class CreateTest {
    @Test
    fun it_successful_access_next_and_complete_using_create_method() {
        val o = Observable.create<Int> {
            it.onNext(1)
            it.onComplete()
        }

        val subscriber = TestObserver<Int>()
        o.subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertComplete()
        subscriber.assertValue(1)
    }

    @Test
    fun it_successful_access_next_error_using_create_method() {
        class CustomException : Exception()

        val o = Observable.create<Int> { it.onError(CustomException()) }

        val subscriber = TestObserver<Int>()
        o.subscribe(subscriber)

        subscriber.assertError(CustomException::class.java)
    }

    @Test
    fun it_successful_access_next_and_complete_using_multiple_subscriber() {
        val o = Observable.create<Int> {
            it.onNext(1)
            it.onComplete()
        }

        val subscriber = TestObserver<Int>()
        o.subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertComplete()
        subscriber.assertValue(1)

        Thread.sleep(1000)

        val subscriber1 = TestObserver<Int>()
        o.subscribe(subscriber1)

        subscriber1.assertNoErrors()
        subscriber1.assertComplete()
        subscriber1.assertValue(1)
    }
}