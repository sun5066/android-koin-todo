package github.sun5066.kointodo.di

import github.sun5066.kointodo.viewmodel.TodoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel { TodoViewModel(get()) }
}