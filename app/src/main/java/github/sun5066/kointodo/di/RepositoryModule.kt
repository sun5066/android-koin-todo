package github.sun5066.kointodo.di

import android.content.Context
import androidx.room.Room
import github.sun5066.kointodo.data.local.db.ToDoDataBase
import github.sun5066.kointodo.data.repository.TodoRepository
import github.sun5066.kointodo.data.repository.TodoRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

internal val repositoryModule = module {

    single<TodoRepository> { TodoRepositoryImpl(get(), get()) }

    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }
}

internal fun provideDB(context: Context): ToDoDataBase =
    Room.databaseBuilder(context, ToDoDataBase::class.java, ToDoDataBase.DB_NAME).build()

internal fun provideToDoDao(database: ToDoDataBase) = database.toDoDao()