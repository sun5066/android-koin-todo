package github.sun5066.kointodo

import github.sun5066.kointodo.base.BaseActivity
import github.sun5066.kointodo.databinding.ActivityMainBinding
import github.sun5066.kointodo.viewmodel.TodoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    val todoViewModel: TodoViewModel by viewModel()

    override fun getResourceId() = R.layout.activity_main

    override fun initDataBinding() {
        todoViewModel.sayHello()
    }

    override fun initView() {

    }
}