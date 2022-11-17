package br.com.kotlinapigithub.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.kotlinapigithub.data.GithubApi
import br.com.kotlinapigithub.data.model.PullRequest
import br.com.kotlinapigithub.data.sources.PullRequestSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PullRequestRepository @Inject constructor(private val githubApi: GithubApi) {

    fun getPullRequests(login: String, repositoryName: String): Flow<PagingData<PullRequest>> {
        return Pager(config = PagingConfig(
            pageSize = 10, maxSize = 30, enablePlaceholders = false
        ), pagingSourceFactory = { PullRequestSource(login, repositoryName, githubApi) }).flow
    }

}