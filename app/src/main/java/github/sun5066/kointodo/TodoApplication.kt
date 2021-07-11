package github.sun5066.kointodo

import android.app.Application
import github.sun5066.kointodo.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TodoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TodoApplication)
            modules(repositoryModule)
        }
    }
}