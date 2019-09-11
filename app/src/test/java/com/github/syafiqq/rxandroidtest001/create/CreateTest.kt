package com.github.syafiqq.rxandroidtest001.create

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test

class CreateTest {
    @Test
    fun it_successful_access_next_and_complete_with_single_value() {
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
    fun it_successful_access_next_error_with_single_value() {
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

    @Test
    fun it_successful_access_next_and_complete_with_reversed_flow() {
        val o = Observable.create<Int> {
            it.onComplete()
            it.onNext(1)
        }

        val subscriber = TestObserver<Int>()
        o.subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertComplete()
        subscriber.assertNoValues()
    }

    @Test
    fun it_successful_access_next_and_complete_with_array() {
        val o = Observable.create<Int> {
            for (i in 1..10) {
                it.onNext(i)
            }

            it.onComplete()
        }

        val subscriber = TestObserver<Int>()
        o.subscribe(subscriber)

        subscriber.assertNoErrors()
        subscriber.assertComplete()
        subscriber.assertValues(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    }
}