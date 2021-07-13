package github.sun5066.kointodo.viewmodel.detail

import github.sun5066.kointodo.data.entity.ToDoEntity

sealed class ToDoDetailState {

    object UnInitialize: ToDoDetailState()

    object Loading: ToDoDetailState()

    data class Success(val todoItem: ToDoEntity): ToDoDetailState()

    object Write: ToDoDetailState()

    object Modify: ToDoDetailState()

    object Delete: ToDoDetailState()

    object Error: ToDoDetailState()
}