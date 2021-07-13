package github.sun5066.kointodo.data.repository

import github.sun5066.kointodo.data.entity.ToDoEntity

class TodoRepositoryImpl: TodoRepository {
    override suspend fun getToDoList(): List<ToDoEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun insertToDo(todoItem: ToDoEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun updateToDo(toDoEntity: ToDoEntity): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getToDoItem(itemId: Long): ToDoEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodoItem(id: Long): Boolean {
        TODO("Not yet implemented")
    }
}