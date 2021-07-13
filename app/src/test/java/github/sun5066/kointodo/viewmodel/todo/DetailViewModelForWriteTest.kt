package github.sun5066.kointodo.viewmodel.todo

import github.sun5066.kointodo.data.entity.ToDoEntity
import github.sun5066.kointodo.viewmodel.ToDoListState
import github.sun5066.kointodo.viewmodel.ToDoViewModel
import github.sun5066.kointodo.viewmodel.ViewModelTest
import github.sun5066.kointodo.viewmodel.detail.DetailMode
import github.sun5066.kointodo.viewmodel.detail.DetailViewModel
import github.sun5066.kointodo.viewmodel.detail.ToDoDetailState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

/**
 * [DetailViewModelForWriteTest]을 테스트 하기 위한 Unit Test Case
 *
 * 1. initData()
 * 2. test viewModel fetch
 * 2. test insert todo
 * */
@ExperimentalCoroutinesApi
internal class DetailViewModelForWriteTest : ViewModelTest() {

    private val id = 0L
    private val detailViewModel: DetailViewModel by inject { parametersOf(DetailMode.WRITE, id) }
    private val listViewModel: ToDoViewModel by inject()

    private val todo = ToDoEntity(
        id, "title: $id", "description: $id", false
    )

    @Test
    fun `test viewModel fetch`() = runBlockingTest {
        val testObservable = detailViewModel.toDoDetailLiveData.test()
        detailViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialize,
                ToDoDetailState.Write
            )
        )
    }

    @Test
    fun `test insert todo`() = runBlockingTest {
        val detailTestObservable = detailViewModel.toDoDetailLiveData.test()
        val listTestObservable = listViewModel.toDoListLiveData.test()

        detailViewModel.writeToDo(
            title = todo.title,
            description = todo.description
        )

        detailTestObservable.assertValueSequence(
            listOf(
                ToDoDetailState.UnInitialize,
                ToDoDetailState.Loading,
                ToDoDetailState.Success(todo)
            )
        )

        assert(detailViewModel.detailMode == DetailMode.DETAIL)
        assert(detailViewModel.id == id)

        // 뒤로 나가서 리스트 보기
        listViewModel.fetchData()
        listTestObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialize,
                ToDoListState.Loading,
                ToDoListState.Success(listOf(todo))
            )
        )
    }
}