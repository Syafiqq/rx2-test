package com.github.syafiqq.rxandroidtest001.create

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.syafiqq.rxandroidtest001.exception.CustomException
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeferTest {
    @Test
    fun it_successful_access_next_and_complete_with_single_value() {
        Observable.defer<Int> {
            Observable.create<Int> {
                it.onNext(1)
                it.onComplete()
            }
        }
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValue(1)
    }

    @Test
    fun it_successful_access_next_error_with_single_value() {
        Observable.defer<Int> {
            Observable.create<Int> {
                it.onError(CustomException())
            }
        }
            .test()
            .assertSubscribed()
            .assertError(CustomException::class.java)
            .assertNotComplete()
            .assertNoValues()
    }

    @Test
    fun it_successful_access_next_and_complete_using_multiple_subscriber() {
        val o = Observable.defer<Int> {
            Observable.create<Int> {
                it.onNext(1)
                it.onComplete()
            }
        }

        o.test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValue(1)

        Thread.sleep(500)

        o.test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValue(1)
    }


    @Test
    fun it_successful_access_next_and_complete_with_reversed_flow() {
        Observable.create<Int> {
            it.onComplete()
            it.onNext(1)
        }
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertNoValues()
    }

    @Test
    fun it_successful_access_next_and_complete_with_array() {
        val o = Observable.defer<Int> {
            Observable.create<Int> {
                for (i in 1..10) {
                    it.onNext(i)
                }

                it.onComplete()
            }
        }
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValues(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    }
}