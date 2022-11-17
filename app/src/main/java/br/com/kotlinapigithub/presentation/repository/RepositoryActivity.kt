package br.com.kotlinapigithub.presentation.repository

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import br.com.kotlinapigithub.databinding.ActivityRepositoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoryActivity : AppCompatActivity() {

    private lateinit var repositoryViewModel: RepositoryViewModel
    private lateinit var binding: ActivityRepositoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepositoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        repositoryViewModel = ViewModelProvider(this)[RepositoryViewModel::class.java]

        initRepositoryList()
    }

    private fun initRepositoryList() {
        val adapter = RepositoryAdapter()

        binding.apply {
            listRepository.adapter = adapter

            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        lifecycleScope.launch {
            repositoryViewModel.repositories.collectLatest {
                adapter.submitData(it)
            }
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progress.isVisible = loadState.source.refresh is LoadState.Loading
                listRepository.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                errorMessage.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                    listRepository.isVisible = false
                    notFound.isVisible = true
                } else notFound.isVisible = false
            }
        }


    }

}