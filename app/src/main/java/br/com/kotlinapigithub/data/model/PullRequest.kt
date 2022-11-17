package br.com.kotlinapigithub.data.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

@JsonIgnoreProperties(ignoreUnknown = true)
@Parcelize
data class PullRequest(
    @JsonProperty("id") val id: Long,
    @JsonProperty("title") val title: String,
    @JsonProperty("user") val user: User,
    @JsonProperty("body") val body: String?,
    @JsonProperty("created_at") val createdAt: String,
    @JsonProperty("html_url") val htmlUrl: String
) : Parcelable
