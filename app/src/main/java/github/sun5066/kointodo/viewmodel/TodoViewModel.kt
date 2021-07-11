package github.sun5066.kointodo.viewmodel

import androidx.lifecycle.ViewModel
import github.sun5066.kointodo.persistence.TodoRepository

class TodoViewModel(private val repository: TodoRepository): ViewModel() {

    fun sayHello() = "${repository.hello()} from $this"
}