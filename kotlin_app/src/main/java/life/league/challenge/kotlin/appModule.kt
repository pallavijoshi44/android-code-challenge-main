package life.league.challenge.kotlin

import life.league.challenge.kotlin.api.Api
import life.league.challenge.kotlin.api.HttpLoginRepository
import life.league.challenge.kotlin.api.Service
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule  = module {

    factory { HttpLoginRepository(Service.api) }
}