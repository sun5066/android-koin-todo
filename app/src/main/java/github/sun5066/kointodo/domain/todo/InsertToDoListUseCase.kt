package github.sun5066.kointodo.domain.todo

import github.sun5066.kointodo.data.entity.ToDoEntity
import github.sun5066.kointodo.data.repository.TodoRepository
import github.sun5066.kointodo.domain.UseCase

internal class InsertToDoListUseCase(private val repository: TodoRepository): UseCase {

    suspend operator fun invoke(toDoList: List<ToDoEntity>) {
        return repository.insertToDoList(toDoList)
    }
}