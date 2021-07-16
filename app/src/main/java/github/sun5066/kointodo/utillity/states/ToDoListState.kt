package github.sun5066.kointodo.utillity.states

import github.sun5066.kointodo.data.entity.ToDoEntity

sealed class ToDoListState {

    object UnInitialize: ToDoListState()

    object Loading: ToDoListState()

    data class Success(val todoList: List<ToDoEntity>): ToDoListState()

    object Error: ToDoListState()
}