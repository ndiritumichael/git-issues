package com.devmike.gitissuesmobile

import android.app.Application
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import io.sentry.android.core.SentryAndroid
import timber.log.Timber

@HiltAndroidApp
class GitIssuesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.setAnalyticsCollectionEnabled(true)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        SentryAndroid.init(this) { options ->
            options.dsn =
                BuildConfig.SENTRYDSN
            // Add a callback that will be used before the event is sent to Sentry.
            // With this callback, you can modify the event or, when returning null, also discard the event.
            options.apply {
                environment = if (BuildConfig.DEBUG) "debug" else "production"
            }
        }
    }
}
