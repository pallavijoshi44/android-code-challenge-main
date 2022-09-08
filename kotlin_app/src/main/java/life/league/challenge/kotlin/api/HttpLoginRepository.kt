package life.league.challenge.kotlin.api

import life.league.challenge.kotlin.model.Account

class HttpLoginRepository(private val api: Api) {

    suspend fun login(credentials: String): Account = api.login("Basic $credentials")
}