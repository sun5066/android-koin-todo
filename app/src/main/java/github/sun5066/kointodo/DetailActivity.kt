package github.sun5066.kointodo

import github.sun5066.kointodo.base.BaseActivity
import github.sun5066.kointodo.databinding.ActivityDetailBinding
import github.sun5066.kointodo.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class DetailActivity: BaseActivity<DetailViewModel, ActivityDetailBinding>() {

    companion object {
        const val DETAIL_MODE_KEY = "ToDoId"
        const val TODO_ID_KEY = "DetailMode"
    }

    override val viewModel: DetailViewModel by viewModel {
        parametersOf(
            intent.getSerializableExtra(DETAIL_MODE_KEY),
            intent.getLongExtra(TODO_ID_KEY, -1)
        )
    }

    override fun getResourceId() = R.layout.activity_detail

    override fun initDataBinding() {

    }

    override fun observeData() {

    }
}