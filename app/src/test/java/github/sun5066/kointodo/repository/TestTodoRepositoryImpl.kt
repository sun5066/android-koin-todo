package github.sun5066.kointodo.repository

import github.sun5066.kointodo.data.entity.ToDoEntity
import github.sun5066.kointodo.data.repository.TodoRepository

class TestTodoRepositoryImpl: TodoRepository {

    private val toDoList = mutableListOf<ToDoEntity>()

    override suspend fun getToDoList(): List<ToDoEntity> = toDoList

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) {
        this.toDoList.addAll(toDoList)
    }
}