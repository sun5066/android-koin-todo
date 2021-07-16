package github.sun5066.kointodo

import android.app.Activity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.sun5066.kointodo.base.BaseActivity
import github.sun5066.kointodo.data.entity.ToDoEntity
import github.sun5066.kointodo.databinding.ActivityMainBinding
import github.sun5066.kointodo.databinding.ViewholderTodoItemBinding
import github.sun5066.kointodo.extentions.showToast
import github.sun5066.kointodo.viewmodel.ToDoListState
import github.sun5066.kointodo.viewmodel.ToDoViewModel
import github.sun5066.kointodo.viewmodel.detail.DetailMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

internal class MainActivity : BaseActivity<ToDoViewModel, ActivityMainBinding>(), CoroutineScope {

    private val activityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) mViewModel.fetchData()
    }
    private val adapter = ToDoAdapter()

    override val mViewModel: ToDoViewModel by viewModel()
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + Job()
    override fun getResourceId(): Int = R.layout.activity_main
    override fun initDataBinding() {}
    override fun observeData() = mViewModel.toDoListLiveData.observe(this) {
        when (it) {
            is ToDoListState.UnInitialize -> initView()
            is ToDoListState.Loading -> handleLoadingState()
            is ToDoListState.Success -> handleSuccessState(it)
            is ToDoListState.Error -> showToast(R.string.error, Toast.LENGTH_LONG)
        }
    }

    private fun initView() = with(mBinding) {
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener {
            mViewModel.fetchData()
        }

        addToDoButton.setOnClickListener {
            activityForResult.launch(DetailActivity.getIntent(this@MainActivity, DetailMode.WRITE))
        }
    }

    private fun handleLoadingState() = with(mBinding) {
        refreshLayout.isRefreshing = true
    }

    private fun handleSuccessState(state: ToDoListState.Success) = with(mBinding) {
        refreshLayout.isEnabled = state.todoList.isNotEmpty()
        refreshLayout.isRefreshing = false

        if (state.todoList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            setAdapterList(state)
        }
    }

    private fun setAdapterList(state: ToDoListState.Success) {
        adapter.setToDoList(
            state.todoList,
            toDoItemClickListener = {
                activityForResult.launch(DetailActivity.getIntent(this@MainActivity, it.id, DetailMode.DETAIL))
            }, toDoCheckListener = {
                mViewModel.updateTodoEntity(it)
            }
        )
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                mViewModel.deleteAll()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_menu, menu)
        return true
    }
}

internal class ToDoAdapter: RecyclerView.Adapter<ToDoAdapter.ToDoItemViewHolder>() {

    private var toDoList: List<ToDoEntity> = listOf()
    private lateinit var toDoItemClickListener: (ToDoEntity) -> Unit
    private lateinit var toDoCheckListener: (ToDoEntity) -> Unit

    inner class ToDoItemViewHolder(
        private val binding: ViewholderTodoItemBinding,
        val toDoItemClickListener: (ToDoEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: ToDoEntity) = with(binding) {
            checkBox.text = data.title
            checkToDoComplete(data.hasCompleted)
        }

        fun bindView(data: ToDoEntity) {
            binding.checkBox.setOnClickListener {
                toDoCheckListener(data.copy(hasCompleted = binding.checkBox.isChecked))
                checkToDoComplete(binding.checkBox.isChecked)
            }
            binding.root.setOnClickListener {
                toDoItemClickListener(data)
            }
        }

        private fun checkToDoComplete(isChecked: Boolean) = with(binding) {
            checkBox.isChecked = isChecked
            container.setBackgroundColor(ContextCompat.getColor(root.context, getContainerBackgroundColor(isChecked)))
        }

        private fun getContainerBackgroundColor(flag: Boolean): Int {
            return if (flag) R.color.gray_300
            else R.color.white
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoAdapter.ToDoItemViewHolder {
        val view = ViewholderTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoItemViewHolder(view, toDoItemClickListener)
    }

    override fun onBindViewHolder(holder: ToDoAdapter.ToDoItemViewHolder, position: Int) {
        holder.bindData(toDoList[position])
        holder.bindView(toDoList[position])
    }

    override fun getItemCount(): Int = toDoList.size

    fun setToDoList(toDoList: List<ToDoEntity>, toDoItemClickListener: (ToDoEntity) -> Unit, toDoCheckListener: (ToDoEntity) -> Unit) {
        this.toDoList = toDoList
        this.toDoItemClickListener = toDoItemClickListener
        this.toDoCheckListener = toDoCheckListener
        notifyDataSetChanged()
    }
}
