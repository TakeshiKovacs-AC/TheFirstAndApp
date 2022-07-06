package ru.netology.myapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.myapp.databinding.PostItemBinding

typealias ClickedButtonListener = (Post) -> Unit

class PostsAdapter(
    private val clickedLike: ClickedButtonListener,
    private val clickedShare: ClickedButtonListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(
        private val binding: PostItemBinding,
        private val clickedLike: ClickedButtonListener,
        private val clickedShare: ClickedButtonListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.shares.setOnClickListener {
                clickedShare(post)
            }
        }
        init {
            binding.likes.setOnClickListener {
                clickedLike(post)
            }
        }

        fun bind(post: Post) {
            this.post = post
            with(binding) {
                authorName.text = post.author
                date.text = post.date
                text.text = post.content
                sharesCount.text = post.share.toString()
                likesCount.text = post.like.toString()
                likes.setImageResource(
                    if (post.isLiked) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, clickedLike, clickedShare)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    private object DiffCallback: DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
        oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
        }
    }

