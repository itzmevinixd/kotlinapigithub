package br.com.kotlinapigithub.data.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.kotlinapigithub.data.GithubApi
import br.com.kotlinapigithub.data.model.PullRequest

private const val STARTING_PAGE_INDEX = 1

class PullRequestSource(
    private val login: String, private val repositoryName: String, private val githubApi: GithubApi
) : PagingSource<Int, PullRequest>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PullRequest> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = githubApi.getPullRequests(login, repositoryName, position, params.loadSize)

            LoadResult.Page(
                data = response,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
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