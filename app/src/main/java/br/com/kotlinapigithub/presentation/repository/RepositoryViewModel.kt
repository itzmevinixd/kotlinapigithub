package br.com.kotlinapigithub.presentation.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.kotlinapigithub.domain.model.Repository
import br.com.kotlinapigithub.domain.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    githubRepository: GithubRepository
) : ViewModel() {

    val repositories: Flow<PagingData<Repository>> = githubRepository.getRepositories()
        .cachedIn(viewModelScope)
}