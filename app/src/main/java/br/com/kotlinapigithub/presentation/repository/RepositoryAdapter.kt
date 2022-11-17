package br.com.kotlinapigithub.presentation.repository

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.kotlinapigithub.R
import br.com.kotlinapigithub.databinding.RepositoryItemViewBinding
import br.com.kotlinapigithub.data.model.Repository
import br.com.kotlinapigithub.presentation.pullrequest.PullRequestActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class RepositoryAdapter :
    PagingDataAdapter<Repository, RepositoryAdapter.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RepositoryItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ViewHolder(private val binding: RepositoryItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: Repository) {

            binding.apply {
                Glide.with(itemView).load(repository.owner.avatarUrl).centerCrop()
                    .error(android.R.drawable.stat_notify_error)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(avatar)

                Glide.with(itemView).load(R.drawable.ic_baseline_star_24).into(star)

                Glide.with(itemView).load(R.drawable.ic_fork).into(fork)

                login.text = repository.owner.login
                description.text = repository.description
                name.text = repository.name
                starCount.text = repository.stars.toString()
                forkCount.text = repository.forks.toString()

                itemView.setOnClickListener {
                    val intent = Intent(it.context, PullRequestActivity::class.java)
                    intent.putExtra("login", repository.owner.login)
                    intent.putExtra("repositoryName", repository.name)
                    it.context.startActivity(intent)
                }
            }
        }
    }

}