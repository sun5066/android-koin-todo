package github.sun5066.kointodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import github.sun5066.kointodo.base.BaseViewModel
import github.sun5066.kointodo.data.entity.ToDoEntity
import github.sun5066.kointodo.domain.todo.DeleteToDoItemUseCase
import github.sun5066.kointodo.domain.todo.GetToDoItemUseCase
import github.sun5066.kointodo.domain.todo.InsertToDoUseCase
import github.sun5066.kointodo.domain.todo.UpdateToDoListUseCase
import github.sun5066.kointodo.utillity.enums.DetailMode
import github.sun5066.kointodo.utillity.states.ToDoDetailState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class DetailViewModel(
    var detailMode: DetailMode,
    var id: Long = -1,
    private val getToDoItemUseCase: GetToDoItemUseCase,
    private val deleteToDoItemUseCase: DeleteToDoItemUseCase,
    private val updateToDoItemUseCase: UpdateToDoListUseCase,
    private val insertToDoItemUseCase: InsertToDoUseCase
) : BaseViewModel() {

    private val _toDoDetailLiveData = MutableLiveData<ToDoDetailState>(ToDoDetailState.UnInitialize)
    val toDoDetailLiveData: LiveData<ToDoDetailState> get() = _toDoDetailLiveData

    override fun fetchData(): Job = viewModelScope.launch {

        when (detailMode) {
            DetailMode.WRITE -> setState(ToDoDetailState.Write)
            DetailMode.DETAIL -> fetchStateDetailMode()
        }
    }

    private suspend fun fetchStateDetailMode() {
        setState(ToDoDetailState.Loading)
        try {
            getToDoItemUseCase(id)?.let {
                setState(ToDoDetailState.Success(it))
            } ?: run {
                setState(ToDoDetailState.Error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            setState(ToDoDetailState.Error)
        }
    }

    fun deleteTodo() = viewModelScope.launch {
        setState(ToDoDetailState.Loading)

        try {
            deleteToDoItemUseCase(id)
            setState(ToDoDetailState.Delete)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            setState(ToDoDetailState.Error)
        }
    }

    fun setModifyMode() = viewModelScope.launch {
        setState(ToDoDetailState.Modify)
    }

    fun writeToDo(title: String, description: String) = viewModelScope.launch {
        setState(ToDoDetailState.Loading)

        when (detailMode) {
            DetailMode.WRITE -> writeStateWriteMode(title, description)
            DetailMode.DETAIL -> writeStateDetailMode(title, description)
        }
    }

    private suspend fun writeStateWriteMode(title: String, description: String) {
        try {
            val toDoEntity = ToDoEntity(title = title, description = description)
            id = insertToDoItemUseCase(toDoEntity)
            setState(ToDoDetailState.Success(toDoEntity))
            detailMode = DetailMode.DETAIL
        } catch (e: Exception) {
            e.printStackTrace()
            setState(ToDoDetailState.Error)
        }
    }

    private suspend fun writeStateDetailMode(title: String, description: String) {
        try {
            getToDoItemUseCase(id)?.let {
                val updateToDoEntity = it.copy(title = title, description = description)
                updateToDoItemUseCase(updateToDoEntity)
                setState(ToDoDetailState.Success(updateToDoEntity))
            } ?: kotlin.run {
                setState(ToDoDetailState.Error)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            setState(ToDoDetailState.Error)
        }
    }

    private fun setState(state: ToDoDetailState) {
        _toDoDetailLiveData.postValue(state)
    }
}