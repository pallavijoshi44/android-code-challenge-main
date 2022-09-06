package life.league.challenge.kotlin.api

class HttpLoginRepository(private val api: Api) {

    suspend fun login(credentials: String) = api.login("Basic $credentials")
}