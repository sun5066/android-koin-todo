package github.sun5066.kointodo.di

import github.sun5066.kointodo.data.repository.TodoRepository
import github.sun5066.kointodo.domain.todo.GetToDoListUseCase
import github.sun5066.kointodo.domain.todo.InsertToDoListUseCase
import github.sun5066.kointodo.repository.TestTodoRepositoryImpl
import github.sun5066.kointodo.viewmodel.TodoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val testAppModule = module {

    // viewModel
    viewModel { TodoViewModel(get()) }

    // UseCase
    factory { GetToDoListUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }

    // Repository
    single<TodoRepository> { TestTodoRepositoryImpl() }
}