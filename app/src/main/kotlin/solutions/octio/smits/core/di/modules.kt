package solutions.octio.smits.core.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import solutions.octio.smits.features.appstate.AppStateViewModel

val appStateModule = module {
    // Presentation
    viewModel { AppStateViewModel() }
}