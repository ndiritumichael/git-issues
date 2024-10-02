package com.devmike.database.helpers

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.devmike.database.GithubDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseDbTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()
    lateinit var db: GithubDatabase

    @Before
    open fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room
                .inMemoryDatabaseBuilder(
                    context,
                    GithubDatabase::class.java,
                ).allowMainThreadQueries()
                .build()
    }

    @After
    open fun tearDownDb() {
        db.clearAllTables()
        db.close()
    }
}
