package github.sun5066.kointodo.data.repository

import github.sun5066.kointodo.data.entity.ToDoEntity
import github.sun5066.kointodo.data.local.db.dao.ToDoDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TodoRepositoryImpl(
    private val toDoDao: ToDoDao,
    private val ioDispatcher: CoroutineDispatcher
) : TodoRepository {
    override suspend fun getToDoList(): List<ToDoEntity> = withContext(ioDispatcher) {
        toDoDao.selectAll()
    }

    override suspend fun getToDoItem(id: Long): ToDoEntity? = withContext(ioDispatcher) {
        toDoDao.findById(id)
    }

    override suspend fun insertToDo(todoItem: ToDoEntity): Long = withContext(ioDispatcher) {
        toDoDao.insert(todoItem)
    }

    override suspend fun insertToDoList(toDoList: List<ToDoEntity>) = withContext(ioDispatcher) {
        toDoDao.insert(toDoList)
    }

    override suspend fun updateToDo(toDoEntity: ToDoEntity) = withContext(ioDispatcher) {
        toDoDao.update(toDoEntity)
    }

    override suspend fun deleteAll() {
        toDoDao.deleteAll()
    }

    override suspend fun deleteTodoItem(id: Long) = withContext(ioDispatcher) {
        toDoDao.delete(id)
    }
}