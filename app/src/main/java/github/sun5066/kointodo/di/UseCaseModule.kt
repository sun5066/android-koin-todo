package github.sun5066.kointodo.di

import android.content.Context
import androidx.room.Room
import github.sun5066.kointodo.data.local.db.ToDoDataBase
import github.sun5066.kointodo.data.repository.TodoRepository
import github.sun5066.kointodo.data.repository.TodoRepositoryImpl
import github.sun5066.kointodo.domain.todo.*
import github.sun5066.kointodo.viewmodel.ToDoViewModel
import github.sun5066.kointodo.viewmodel.detail.DetailMode
import github.sun5066.kointodo.viewmodel.detail.DetailViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val useCaseModule = module {

    single { Dispatchers.Main }
    single { Dispatchers.IO }

    // UseCase
    factory { GetToDoListUseCase(get()) }
    factory { GetToDoItemUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }
    factory { InsertToDoUseCase(get()) }
    factory { UpdateToDoListUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }

    // Repository
    single<TodoRepository> { TodoRepositoryImpl(get(), get()) }

    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }

    // viewModel
    viewModel { ToDoViewModel(get(), get(), get()) }
    viewModel { (detailMode: DetailMode, id: Long) -> DetailViewModel(detailMode, id, get(), get(), get(), get()) }
}

internal fun provideDB(context: Context): ToDoDataBase =
    Room.databaseBuilder(context, ToDoDataBase::class.java, ToDoDataBase.DB_NAME).build()

internal fun provideToDoDao(database: ToDoDataBase) = database.toDoDao()