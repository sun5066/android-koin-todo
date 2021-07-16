package github.sun5066.kointodo.di

import github.sun5066.kointodo.viewmodel.ToDoViewModel
import github.sun5066.kointodo.viewmodel.detail.DetailMode
import github.sun5066.kointodo.viewmodel.detail.DetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {

    viewModel { ToDoViewModel(get(), get(), get()) }
    viewModel { (detailMode: DetailMode, id: Long) -> DetailViewModel(detailMode, id, get(), get(), get(), get()) }
}