package br.com.kotlinapigithub.data.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class Owner(
    @JsonProperty("id") val id: Int,
    @JsonProperty("login") val login: String,
    @JsonProperty("avatar_url") val avatarUrl: String
) : Parcelable