package github.sun5066.kointodo.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.view.isGone
import github.sun5066.kointodo.R
import github.sun5066.kointodo.base.BaseActivity
import github.sun5066.kointodo.databinding.ActivityDetailBinding
import github.sun5066.kointodo.extentions.showToast
import github.sun5066.kointodo.utillity.enums.DetailMode
import github.sun5066.kointodo.utillity.states.ToDoDetailState
import github.sun5066.kointodo.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class DetailActivity : BaseActivity<DetailViewModel, ActivityDetailBinding>() {

    companion object {
        const val DETAIL_MODE_KEY = "ToDoId"
        const val TODO_ID_KEY = "DetailMode"
        const val FETCH_REQUEST_CODE = 10

        fun getIntent(context: Context, detailModel: DetailMode) =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(DETAIL_MODE_KEY, detailModel)
            }

        fun getIntent(context: Context, id: Long, detailModel: DetailMode) =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(TODO_ID_KEY, id)
                putExtra(DETAIL_MODE_KEY, detailModel)
            }
    }

    override val mViewModel: DetailViewModel by viewModel {
        parametersOf(
            intent.getSerializableExtra(DETAIL_MODE_KEY),
            intent.getLongExtra(TODO_ID_KEY, -1)
        )
    }

    override fun getResourceId() = R.layout.activity_detail

    override fun initDataBinding() {
        setResult(Activity.RESULT_OK)
    }

    override fun observeData() = mViewModel.toDoDetailLiveData.observe(this) {
        when (it) {
            is ToDoDetailState.UnInitialize -> initView(mBinding)
            is ToDoDetailState.Loading -> handleLoadingState()
            is ToDoDetailState.Success -> handleSuccessState(it)
            is ToDoDetailState.Modify -> handleModifyState()
            is ToDoDetailState.Delete -> showToast(R.string.delete_success, Toast.LENGTH_LONG)
            is ToDoDetailState.Error -> showToast(R.string.error, Toast.LENGTH_LONG)
            is ToDoDetailState.Write -> handleWriteState()
        }
    }

    private fun initView(binding: ActivityDetailBinding) = with(binding) {
        titleInput.isEnabled = false
        descriptionInput.isEnabled = false

        deleteButton.isGone = true
        modifyButton.isGone = true
        updateButton.isGone = true

        deleteButton.setOnClickListener {
            mViewModel.deleteTodo()
        }
        modifyButton.setOnClickListener {
            mViewModel.setModifyMode()
        }
        updateButton.setOnClickListener {
            mViewModel.writeToDo(
                title = titleInput.text.toString(),
                description = descriptionInput.text.toString()
            )
        }
    }

    private fun handleLoadingState() = with(mBinding) {
        progressBar.isGone = false
    }

    private fun handleModifyState() = with(mBinding) {
        titleInput.isEnabled = true
        descriptionInput.isEnabled = true

        deleteButton.isGone = true
        modifyButton.isGone = true
        updateButton.isGone = false
    }

    private fun handleWriteState() = with(mBinding) {
        titleInput.isEnabled = true
        descriptionInput.isEnabled = true

        updateButton.isGone = false
    }

    private fun handleSuccessState(state: ToDoDetailState.Success) = with(mBinding) {
        progressBar.isGone = true

        titleInput.isEnabled = false
        descriptionInput.isEnabled = false

        deleteButton.isGone = false
        modifyButton.isGone = false
        updateButton.isGone = true

        val toDoItem = state.todoItem
        titleInput.setText(toDoItem.title)
        descriptionInput.setText(toDoItem.description)
    }
}