package com.github.syafiqq.rxandroidtest001.observable.create

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class JustTest {
    @Test
    fun it_should_create_with_single_value() {
        Observable.just(1)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValue(1)
    }

    @Test
    fun it_should_create_with_multiple_value() {
        Observable.just(1, 2, 3)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValues(1, 2, 3)
    }

    @Test
    fun it_should_create_with_array() {
        val obj = arrayOf(1)
        Observable.just(obj)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValues(obj)
    }

    @Test
    fun it_should_create_with_iterable() {
        val obj = listOf(1, 2).asIterable()
        Observable.just(obj)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValues(obj)
    }

    @Test
    fun it_should_create_with_obj() {
        val obj = Object()
        Observable.just(obj)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValues(obj)
    }

    @Test(expected = NullPointerException::class)
    fun it_should_not_create_with_null() {
        val obj = null as Int?
        Observable.just(obj)
    }
}