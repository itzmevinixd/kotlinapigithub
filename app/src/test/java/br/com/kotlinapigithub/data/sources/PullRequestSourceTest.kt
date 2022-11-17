package br.com.kotlinapigithub.data.sources

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import br.com.kotlinapigithub.data.GithubApi
import br.com.kotlinapigithub.data.model.PullRequest
import br.com.kotlinapigithub.data.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class PullRequestSourceTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var githubApi: GithubApi
    lateinit var pullRequestSource: PullRequestSource

    companion object {
        val pullRequests = listOf(
            PullRequest(
                id = 0,
                title = "",
                user = User(login = "", avatarUrl = ""),
                body = "",
                createdAt = "",
                htmlUrl = ""
            )
        )
        val nextPullRequests = listOf(
            PullRequest(
                id = 1,
                title = "",
                user = User(login = "", avatarUrl = ""),
                body = "",
                createdAt = "",
                htmlUrl = ""
            )
        )

        const val login = "login"
        const val repositoryName = "repositoryName"
    }

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        pullRequestSource = PullRequestSource(login, repositoryName, githubApi)
    }

    @Test
    fun pagingSourceRefreshSuccess() = runTest {
        given(githubApi.getPullRequests(login, repositoryName, 0, 1)).willReturn(pullRequests)
        val expectedResult = PagingSource.LoadResult.Page(
            data = pullRequests.map {
                PullRequest(
                    id = it.id,
                    title = it.title,
                    user = it.user,
                    body = it.body,
                    createdAt = it.createdAt,
                    htmlUrl = it.htmlUrl
                )
            }, prevKey = -1, nextKey = 1
        )
        assertEquals(
            expectedResult, pullRequestSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0, loadSize = 1, placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun pagingSourceAppendSuccess() = runTest {
        given(githubApi.getPullRequests(login, repositoryName, 1, 1)).willReturn(nextPullRequests)
        val expectedResult = PagingSource.LoadResult.Page(
            data = nextPullRequests.map {
                PullRequest(
                    id = it.id,
                    title = it.title,
                    user = it.user,
                    body = it.body,
                    createdAt = it.createdAt,
                    htmlUrl = it.htmlUrl
                )
            }, prevKey = null, nextKey = 2
        )
        assertEquals(
            expectedResult, pullRequestSource.load(
                PagingSource.LoadParams.Append(
                    key = 1, loadSize = 1, placeholdersEnabled = false
                )
            )
        )
    }

}