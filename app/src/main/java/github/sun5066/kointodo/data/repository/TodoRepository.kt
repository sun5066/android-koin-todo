package github.sun5066.kointodo.data.repository

import github.sun5066.kointodo.data.entity.ToDoEntity

/**
 * 1. insertToDoList
 * 2. getToDoList
 * 3. updateToDo
 * */
interface TodoRepository {
    suspend fun getToDoList(): List<ToDoEntity>

    suspend fun insertToDoList(toDoList: List<ToDoEntity>)

    suspend fun insertToDo(todoItem: ToDoEntity): Long

    suspend fun updateToDo(toDoEntity: ToDoEntity)

    suspend fun getToDoItem(itemId: Long): ToDoEntity?

    suspend fun deleteAll()

    suspend fun deleteTodoItem(id: Long)

}