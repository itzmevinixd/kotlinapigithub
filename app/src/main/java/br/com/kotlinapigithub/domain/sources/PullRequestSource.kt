package br.com.kotlinapigithub.domain.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.kotlinapigithub.data.GithubApi
import br.com.kotlinapigithub.domain.model.PullRequest

private const val STARTING_PAGE_INDEX = 1

class PullRequestSource(
    private val login: String, private val repositoryName: String, private val githubApi: GithubApi
) : PagingSource<Int, PullRequest>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PullRequest> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = githubApi.getPullRequests(login, repositoryName, page, params.loadSize)

            LoadResult.Page(
                data = response,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PullRequest>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}