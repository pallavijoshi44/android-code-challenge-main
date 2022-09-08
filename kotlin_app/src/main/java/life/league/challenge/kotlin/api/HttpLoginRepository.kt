package life.league.challenge.kotlin.api

import arrow.core.Either
import life.league.challenge.kotlin.model.Account

class HttpLoginRepository(private val api: Api) {

    suspend fun login(credentials: String): Either<Throwable, Account> =
        Either.catch { api.login("Basic $credentials") }

}