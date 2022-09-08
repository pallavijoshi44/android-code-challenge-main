package life.league.challenge.kotlin.model

data class UserDetails(val avatar: String?,
                  val userName: String,
                  val title: String,
                  val description: String? = null)