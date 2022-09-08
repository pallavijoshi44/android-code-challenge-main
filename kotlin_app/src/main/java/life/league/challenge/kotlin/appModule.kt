package life.league.challenge.kotlin

import life.league.challenge.kotlin.api.HttpLoginRepository
import life.league.challenge.kotlin.api.HttpUsersRepository
import life.league.challenge.kotlin.api.Service
import life.league.challenge.kotlin.main.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { (encodedCredentials: String) -> MainActivityViewModel(get(), encodedCredentials) }
    factory { HttpLoginRepository(Service.api) }
    factory { HttpUsersRepository(get(), Service.api) }
}