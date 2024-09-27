package com.devmike.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MainCoroutineRule
    @OptIn(ExperimentalCoroutinesApi::class)
    constructor(
        val testDispatcher: TestDispatcher = StandardTestDispatcher(),
    ) : TestWatcher() {
        @OptIn(ExperimentalCoroutinesApi::class)
        override fun starting(description: Description) {
            Dispatchers.setMain(testDispatcher)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        override fun finished(description: Description) {
            Dispatchers.resetMain()
        }
    }
