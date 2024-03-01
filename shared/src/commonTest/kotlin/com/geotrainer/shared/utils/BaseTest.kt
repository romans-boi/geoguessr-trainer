package com.geotrainer.shared.utils

import co.touchlab.kermit.CommonWriter
import co.touchlab.kermit.Logger

import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("diktat")
abstract class BaseTest<T> : AsyncTest() {
    private var _sut: T? = null
    val sut: T
        get() {
            val internalSut = _sut ?: createSut(testScope).also {
                _sut = it
            }
            testScope.runCurrent()
            return internalSut
        }

    abstract fun createSut(testScope: CoroutineScope): T
}

@OptIn(ExperimentalCoroutinesApi::class)
open class AsyncTest {
    protected val testScope = TestScope()
    private lateinit var testDispatcher: TestDispatcher

    open suspend fun suspendBeforeTest() {
        // Override if needed
    }

    protected fun runBlockingTest(
        testBody: suspend TestScope.() -> Unit
    ) {
        testDispatcher = StandardTestDispatcher(testScope.testScheduler)
        Logger.setLogWriters(CommonWriter())
        Dispatchers.setMain(testDispatcher)
        runTest(testDispatcher, timeout = 1.seconds, testBody = {
            suspendBeforeTest()
            testBody()
        })
        Dispatchers.resetMain()
    }
}
