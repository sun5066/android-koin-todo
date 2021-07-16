package github.sun5066.kointodo.di

import github.sun5066.kointodo.data.repository.TodoRepository
import github.sun5066.kointodo.data.repository.TodoRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

internal val repositoryModule = module {

    single<TodoRepository> { TodoRepositoryImpl(get(), get()) }

    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }
}