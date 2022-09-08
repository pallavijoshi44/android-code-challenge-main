package life.league.challenge.kotlin.model

data class User(
        val id: Int,
        val avatar: String? = null,
        val username: String,
)