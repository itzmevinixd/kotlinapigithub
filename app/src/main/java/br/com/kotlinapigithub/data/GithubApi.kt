package br.com.kotlinapigithub.data

import br.com.kotlinapigithub.domain.model.PullRequest
import br.com.kotlinapigithub.data.model.RepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories?q=language:kotlin&sort=stars")
    suspend fun getRepositories(
        @Query("page") page: Int, @Query("per_page") itemsPerPage: Int
    ): RepositoryResponse

    @GET("repos/{login}/{repository}/pulls")
    suspend fun getPullRequests(
        @Path(value = "login") login: String,
        @Path(value = "repository") repositoryName: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): List<PullRequest>

}