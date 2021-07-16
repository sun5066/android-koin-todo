package github.sun5066.kointodo.viewmodel.todo

import github.sun5066.kointodo.data.entity.ToDoEntity
import github.sun5066.kointodo.domain.todo.GetToDoItemUseCase
import github.sun5066.kointodo.domain.todo.InsertToDoListUseCase
import github.sun5066.kointodo.utillity.states.ToDoListState
import github.sun5066.kointodo.viewmodel.ToDoViewModel
import github.sun5066.kointodo.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.core.component.inject

/**
 * [ListViewModel]을 테스트 하기 위한 Unit Test Case
 *
 * 1. initData()
 * 2. test viewModel fetch
 * 3. test Item Update
 * 4. test Item Delete All
 * */
@ExperimentalCoroutinesApi
internal class ListViewModelTest : ViewModelTest() {

    private val viewModel: ToDoViewModel by inject()
    private val insertToDoUseCase: InsertToDoListUseCase by inject()
    private val getToDoItemUseCase: GetToDoItemUseCase by inject()
    private val todoList = (0 until 10).map {
        ToDoEntity(it.toLong(), "title: $it", "description: $it", false)
    }

    /**
     * 필요한 UseCase 들
     * 1. InsertTodoListUseCase
     * 2. GetToDoItemUseCase
     * */

    @Before
    fun init() {
        initData()
    }

    private fun initData() = runBlockingTest {
        insertToDoUseCase(todoList)
    }

    // Test : 입력된 데이터를 불러와서 검증한다.
    @Test
    fun `test viewModel fetch`(): Unit = runBlockingTest {
        val testObservable = viewModel.toDoListLiveData.test()
        viewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialize,
                ToDoListState.Loading,
                ToDoListState.Success(todoList)
            )
        )
    }

    // Test : 데이터를 업데이트 했을 때 잘 반영되는가
    @Test
    fun `test Item Update`(): Unit = runBlockingTest {
        val todo = ToDoEntity(
            1,
            "title: 1",
            "description: 1",
            true
        )
        viewModel.updateTodoEntity(todo)
        assert(getToDoItemUseCase(todo.id)?.hasCompleted ?: false == todo.hasCompleted)
    }

    // Test : 데이터를 다 날렸을때 빈 상태로 보여지는가
    @Test
    fun `test Item Delete All`(): Unit = runBlockingTest {
        val testObservable = viewModel.toDoListLiveData.test()
        viewModel.deleteAll()

        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialize,
                ToDoListState.Loading,
                ToDoListState.Success(listOf())
            )
        )
    }
}