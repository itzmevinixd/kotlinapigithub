package br.com.kotlinapigithub.data.sources

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import br.com.kotlinapigithub.data.GithubApi
import br.com.kotlinapigithub.data.model.Owner
import br.com.kotlinapigithub.data.model.Repository
import br.com.kotlinapigithub.data.model.RepositoryResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class RepositorySourceTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var githubApi: GithubApi
    lateinit var repositorySource: RepositorySource

    companion object {
        val repositoryResponse = RepositoryResponse(
            items = listOf(
                Repository(
                    id = 0,
                    name = "",
                    fullName = "",
                    description = "",
                    stars = 0,
                    forks = 0,
                    owner = Owner(id = 0, login = "", avatarUrl = "")
                )
            )
        )
        val nextRepositoryResponse = RepositoryResponse(
            items = listOf(
                Repository(
                    id = 1,
                    name = "",
                    fullName = "",
                    description = "",
                    stars = 0,
                    forks = 0,
                    owner = Owner(id = 0, login = "", avatarUrl = "")
                )
            )
        )
    }

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repositorySource = RepositorySource(githubApi)
    }

    @Test
    fun pagingSourceReturnsNull() = runTest {
        BDDMockito.given(githubApi.getRepositories(1, 10)).willReturn(null)
        val expectedResult = PagingSource.LoadResult.Error<Int, Repository>(NullPointerException())
        assertEquals(
            expectedResult.toString(), repositorySource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0, loadSize = 1, placeholdersEnabled = false
                )
            ).toString()
        )
    }

    @Test
    fun pagingSourceRefreshSuccess() = runTest {
        BDDMockito.given(githubApi.getRepositories(0, 1)).willReturn(repositoryResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = repositoryResponse.items.map {
                Repository(
                    id = it.id,
                    name = it.name,
                    fullName = it.fullName,
                    description = it.description,
                    stars = it.stars,
                    forks = it.forks,
                    owner = it.owner
                )
            }, prevKey = -1, nextKey = 1
        )
        assertEquals(
            expectedResult, repositorySource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0, loadSize = 1, placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun pagingSourceAppendSuccess() = runTest {
        BDDMockito.given(githubApi.getRepositories(1, 1)).willReturn(nextRepositoryResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = nextRepositoryResponse.items.map {
                Repository(
                    id = it.id,
                    name = it.name,
                    fullName = it.fullName,
                    description = it.description,
                    stars = it.stars,
                    forks = it.forks,
                    owner = it.owner
                )
            }, prevKey = null, nextKey = 2
        )
        assertEquals(
            expectedResult, repositorySource.load(
                PagingSource.LoadParams.Append(
                    key = 1, loadSize = 1, placeholdersEnabled = false
                )
            )
        )
    }

}