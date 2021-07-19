package github.sun5066.kointodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import github.sun5066.kointodo.base.BaseViewModel
import github.sun5066.kointodo.data.entity.ToDoEntity
import github.sun5066.kointodo.domain.todo.DeleteAllToDoItemUseCase
import github.sun5066.kointodo.domain.todo.GetToDoListUseCase
import github.sun5066.kointodo.domain.todo.UpdateToDoListUseCase
import github.sun5066.kointodo.utillity.states.ToDoListState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * 필요한 UseCase
 * 1. [GetToDoListUseCase]
 * 2. [UpdateToDoUseCase]
 * 3. [DeleteAllTodoItemUseCase]
 * */
internal class ToDoViewModel(
    private val getToDoListUseCase: GetToDoListUseCase,
    private val updateTodoUseCse: UpdateToDoListUseCase,
    private val deleteTodoUseCse: DeleteAllToDoItemUseCase
) : BaseViewModel() {

    private val _toDoListLiveData = MutableLiveData<ToDoListState>(ToDoListState.UnInitialize)
    val toDoListLiveData: LiveData<ToDoListState> get() = _toDoListLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        setState(ToDoListState.Loading)
        setState(ToDoListState.Success(getToDoListUseCase()))
    }

    fun updateTodoEntity(toDoItem: ToDoEntity) = viewModelScope.launch {
        updateTodoUseCse(toDoItem)
    }

    fun deleteAll() = viewModelScope.launch {
        setState(ToDoListState.Loading)
        deleteTodoUseCse()
        setState(ToDoListState.Success(getToDoListUseCase()))
    }

    private fun setState(state: ToDoListState) {
        _toDoListLiveData.postValue(state)
    }
}