package github.sun5066.kointodo.di

import github.sun5066.kointodo.data.repository.TodoRepository
import github.sun5066.kointodo.domain.todo.*
import github.sun5066.kointodo.repository.TestTodoRepositoryImpl
import github.sun5066.kointodo.viewmodel.ToDoViewModel
import github.sun5066.kointodo.utillity.enums.DetailMode
import github.sun5066.kointodo.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val testAppModule = module {

    // viewModel
    viewModel { ToDoViewModel(get(), get(), get()) }
    viewModel { (detailMode: DetailMode, id: Long) ->
        DetailViewModel(detailMode, id, get(), get(), get(), get())
    }

    // UseCase
    factory { GetToDoListUseCase(get()) }
    factory { GetToDoItemUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }
    factory { InsertToDoUseCase(get()) }
    factory { UpdateToDoListUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }

    // Repository
    single<TodoRepository> { TestTodoRepositoryImpl() }
}