package github.sun5066.kointodo.data.local.db.dao

import androidx.room.*
import github.sun5066.kointodo.data.entity.ToDoEntity

@Dao
interface ToDoDao {

    @Query("SELECT * FROM ToDoEntity")
    suspend fun selectAll(): List<ToDoEntity>

    @Query("SELECT * FROM ToDoEntity WHERE id=:id")
    suspend fun findById(id: Long): ToDoEntity?

    @Insert
    suspend fun insert(toDoEntity: ToDoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todoList: List<ToDoEntity>)

    @Query("DELETE FROM ToDoEntity WHERE id=:id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM ToDoEntity")
    suspend fun deleteAll()

    @Update
    suspend fun update(toDoEntity: ToDoEntity)
}