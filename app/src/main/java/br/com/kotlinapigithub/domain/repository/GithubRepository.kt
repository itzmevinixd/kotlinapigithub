package br.com.kotlinapigithub.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.kotlinapigithub.data.GithubApi
import br.com.kotlinapigithub.domain.model.Repository
import br.com.kotlinapigithub.domain.sources.RepositorySource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepository @Inject constructor(private val githubApi: GithubApi) {

    fun getRepositories(): Flow<PagingData<Repository>> {
        return Pager(config = PagingConfig(
            pageSize = 10, maxSize = 30, enablePlaceholders = false
        ), pagingSourceFactory = { RepositorySource(githubApi) }).flow
    }

}