package br.com.kotlinapigithub.domain.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.kotlinapigithub.data.GithubApi
import br.com.kotlinapigithub.domain.model.Repository

private const val STARTING_PAGE_INDEX = 1

class RepositorySource(
    private val githubApi: GithubApi
) : PagingSource<Int, Repository>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = githubApi.getRepositories(page, params.loadSize)
            val repositories = response.items

            LoadResult.Page(
                data = repositories,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (repositories.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}