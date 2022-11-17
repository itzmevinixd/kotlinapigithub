package br.com.kotlinapigithub.presentation.pullrequest

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.kotlinapigithub.domain.model.PullRequest
import br.com.kotlinapigithub.databinding.PullRequestItemViewBinding
import br.com.kotlinapigithub.utilities.DateFormatter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class PullRequestAdapter :
    PagingDataAdapter<PullRequest, PullRequestAdapter.ViewHolder>(PR_COMPARATOR) {

    companion object {
        private val PR_COMPARATOR = object : DiffUtil.ItemCallback<PullRequest>() {
            override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PullRequestItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ViewHolder(private val binding: PullRequestItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pullRequest: PullRequest) {

            binding.apply {
                Glide.with(itemView).load(pullRequest.user.avatarUrl).centerCrop()
                    .error(android.R.drawable.stat_notify_error)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(avatar)

                login.text = pullRequest.user.login
                title.text = pullRequest.title
                body.text = pullRequest.body
                createdAt.text = DateFormatter().formatDate(pullRequest.createdAt)

                itemView.setOnClickListener {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(pullRequest.htmlUrl))
                    it.context.startActivity(browserIntent)
                }
            }
        }
    }

}