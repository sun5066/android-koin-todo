package github.sun5066.kointodo.repository

import github.sun5066.kointodo.data.entity.ToDoEntity
import github.sun5066.kointodo.data.repository.TodoRepository

class TestTodoRepositoryImpl : TodoRepository {

    private val toDoList = mutableListOf<ToDoEntity>()

    override suspend fun getToDoList(): List<ToDoEntity> = toDoList

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) {
        this.toDoList.addAll(toDoList)
    }

    override suspend fun insertToDo(todoItem: ToDoEntity): Long {
        this.toDoList.add(todoItem)
        return todoItem.id
    }

    override suspend fun updateToDo(toDoEntity: ToDoEntity): Boolean {
        val foundToDoEntity = toDoList.find { it.id == toDoEntity.id }
        return foundToDoEntity?.run {
            toDoList[toDoList.indexOf(foundToDoEntity)] = toDoEntity
            true
        } ?: false
    }

    override suspend fun getToDoItem(itemId: Long): ToDoEntity? {
        return this.toDoList.find { it.id == itemId }
    }

    override suspend fun deleteAll() {
        this.toDoList.clear()
    }

    override suspend fun deleteTodoItem(id: Long): Boolean {
        this.toDoList.find { it.id == id }?.let { this.toDoList.removeAt(this.toDoList.indexOf(it)) }?.apply { return true }
        return false
    }
}