package github.sun5066.kointodo.di

import android.content.Context
import androidx.room.Room
import github.sun5066.kointodo.data.local.db.ToDoDataBase
import github.sun5066.kointodo.domain.todo.*
import kotlinx.coroutines.Dispatchers
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
}

internal fun provideDB(context: Context): ToDoDataBase =
    Room.databaseBuilder(context, ToDoDataBase::class.java, ToDoDataBase.DB_NAME).build()

internal fun provideToDoDao(database: ToDoDataBase) = database.toDoDao()