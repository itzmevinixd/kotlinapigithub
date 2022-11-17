package br.com.kotlinapigithub.presentation.pullrequest

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.kotlinapigithub.domain.model.PullRequest
import br.com.kotlinapigithub.domain.repository.PullRequestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PullRequestViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, pullRequestRepository: PullRequestRepository
) : ViewModel() {

    val pullRequests: Flow<PagingData<PullRequest>> =
        pullRequestRepository.getPullRequests(savedStateHandle["login"] ?: "", savedStateHandle["repositoryName"] ?: "")
            .cachedIn(viewModelScope)
}