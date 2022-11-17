package br.com.kotlinapigithub.data.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class Repository(
    @JsonProperty("id") val id: Long,
    @JsonProperty("name") val name: String,
    @JsonProperty("full_name") val fullName: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("stargazers_count") val stars: Int,
    @JsonProperty("forks_count") val forks: Int,
    @JsonProperty("owner") val owner: Owner
) : Parcelable