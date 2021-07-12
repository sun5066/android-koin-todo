package github.sun5066.kointodo.data.repository

import github.sun5066.kointodo.data.entity.ToDoEntity

/**
 * 1. insertToDoList
 * 2. getToDoList
 * */
interface TodoRepository {
    suspend fun getToDoList(): List<ToDoEntity>

    suspend fun insertToDoList(toDoList: List<ToDoEntity>)
}