package com.team_gdb.pentatonic.base

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.team_gdb.pentatonic.BuildConfig
import com.team_gdb.pentatonic.di.moduleList
import com.team_gdb.pentatonic.util.Prefs
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class BaseApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private lateinit var firebaseAnalytics: FirebaseAnalytics
        lateinit var instance: BaseApplication
        fun applicationContext(): Context {
            return instance.applicationContext
        }

        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        FirebaseApp.initializeApp(applicationContext())
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = Firebase.analytics

        prefs = Prefs(applicationContext)
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            androidFileProperties()
            modules(modules = moduleList)
        }

    }
}