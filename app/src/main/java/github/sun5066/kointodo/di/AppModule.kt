package github.sun5066.kointodo.di

import github.sun5066.kointodo.persistence.TodoRepository
import github.sun5066.kointodo.persistence.TodoRepositoryImpl
import github.sun5066.kointodo.viewmodel.TodoViewModel
import org.koin.dsl.module

val repositoryModule = module {
    single<TodoRepository> { TodoRepositoryImpl() }

    factory { TodoViewModel(get()) }
}