package github.sun5066.kointodo.data.repository

import github.sun5066.kointodo.data.entity.ToDoEntity

class TodoRepositoryImpl: TodoRepository {
    override suspend fun getToDoList(): List<ToDoEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) {
        TODO("Not yet implemented")
    }
}