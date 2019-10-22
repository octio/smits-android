package solutions.octio.smits

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import solutions.octio.smits.core.di.appStateModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appStateModule
                )
            )
        }
    }
}