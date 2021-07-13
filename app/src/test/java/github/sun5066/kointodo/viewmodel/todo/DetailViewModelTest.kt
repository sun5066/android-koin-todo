package github.sun5066.kointodo.viewmodel.todo

import github.sun5066.kointodo.data.entity.ToDoEntity
import github.sun5066.kointodo.domain.todo.InsertToDoUseCase
import github.sun5066.kointodo.viewmodel.ToDoListState
import github.sun5066.kointodo.viewmodel.ToDoViewModel
import github.sun5066.kointodo.viewmodel.ViewModelTest
import github.sun5066.kointodo.viewmodel.detail.DetailMode
import github.sun5066.kointodo.viewmodel.detail.DetailViewModel
import github.sun5066.kointodo.viewmodel.detail.ToDoDetailState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf


/**
 * [DetailViewModelTest]을 테스트 하기 위한 Unit Test Case
 *
 * 1. initData()
 * 2. test viewModel fetch
 * 3. test delete todo
 * 4. test update todo
 * */
@ExperimentalCoroutinesApi
internal class DetailViewModelTest : ViewModelTest() {

    private val id = 1L
    private val detailViewModel: DetailViewModel by inject { parametersOf(DetailMode.DETAIL, id) }
    private val listViewModel: ToDoViewModel by inject()

    private val todo = ToDoEntity(
        id, "title: $id", "description: $id", false
    )
    private val insertToDoUseCase: InsertToDoUseCase by inject()

    @Before
    fun init() {
        initData()
    }

    private fun initData() = runBlockingTest {
        insertToDoUseCase(todo)
    }

    @Test
    fun `test viewModel fetch`() = runBlockingTest {
        val testObservable = detailViewModel.toDoDetailLiveData.test()
        detailViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialize,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(todo)
            )
        )
    }

    @Test
    fun `test delete todo`() = runBlockingTest {
        val detailTestObservable = detailViewModel.toDoDetailLiveData.test()
        detailViewModel.deleteTodo()

        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialize,
                ToDoDetailState.Loading,
                ToDoDetailState.Delete
            )
        )

        val listTestObservable = listViewModel.toDoListLiveData.test()
        listViewModel.fetchData()
        listTestObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialize,
                ToDoListState.Loading,
                ToDoListState.Success(listOf())
            )
        )
    }

    @Test
    fun `test update todo`() = runBlockingTest {
        val testObservable = detailViewModel.toDoDetailLiveData.test()
        val updateTitle = "title 1 update"
        val updateDescription = "Description 1 update"

        val updateToDo = todo.copy(
            title = updateTitle,
            description = updateDescription
        )

        detailViewModel.writeToDo(
            title = updateTitle,
            description = updateDescription
        )

        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialize,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(updateToDo)
            )
        )
    }
}