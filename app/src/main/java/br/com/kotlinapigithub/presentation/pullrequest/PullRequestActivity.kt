package br.com.kotlinapigithub.presentation.pullrequest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import br.com.kotlinapigithub.databinding.ActivityPullRequestBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PullRequestActivity : AppCompatActivity() {

    private lateinit var pullRequestViewModel: PullRequestViewModel
    private lateinit var binding: ActivityPullRequestBinding

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPullRequestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pullRequestViewModel = ViewModelProvider(this)[PullRequestViewModel::class.java]

        initPullRequestList()
    }

    private fun initPullRequestList() {
        val adapter = PullRequestAdapter()

        binding.apply {
            pullRequestList.adapter = adapter

            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        lifecycleScope.launch {
            pullRequestViewModel.pullRequests.collectLatest {
                adapter.submitData(it)
            }
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progress.isVisible = loadState.source.refresh is LoadState.Loading
                pullRequestList.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                errorMessage.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                    pullRequestList.isVisible = false
                    notFound.isVisible = true
                } else notFound.isVisible = false
            }
        }
    }
}