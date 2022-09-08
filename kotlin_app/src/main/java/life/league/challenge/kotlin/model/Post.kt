package life.league.challenge.kotlin.model

import com.google.gson.annotations.SerializedName

data class Post(val userId: Int,
                val title: String,
                @SerializedName("body") val description: String? = null)