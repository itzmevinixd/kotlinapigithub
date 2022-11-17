package br.com.kotlinapigithub.data.model

import br.com.kotlinapigithub.domain.model.Repository
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class RepositoryResponse(
    @JsonProperty("items") val items: List<Repository> = emptyList(),
)