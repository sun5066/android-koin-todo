package github.sun5066.kointodo.di

import github.sun5066.kointodo.viewmodel.ToDoViewModel
import github.sun5066.kointodo.utillity.enums.DetailMode
import github.sun5066.kointodo.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {

    viewModel { ToDoViewModel(get(), get(), get()) }
    viewModel { (detailMode: DetailMode, id: Long) -> DetailViewModel(detailMode, id, get(), get(), get(), get()) }
}