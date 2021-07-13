package github.sun5066.kointodo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import github.sun5066.kointodo.data.entity.ToDoEntity
import github.sun5066.kointodo.data.local.db.dao.ToDoDao

@Database(entities = [ToDoEntity::class], version = 1, exportSchema = false)
abstract class ToDoDataBase: RoomDatabase() {

    companion object {
        const val DB_NAME = "ToDoDataBase.db"
    }

    abstract fun toDoDao(): ToDoDao
}