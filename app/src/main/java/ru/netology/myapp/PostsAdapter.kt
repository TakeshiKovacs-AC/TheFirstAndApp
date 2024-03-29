package ru.netology.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.myapp.databinding.PostItemBinding

class PostsAdapter(
    private val postListener: PostListener,
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(
        private val binding: PostItemBinding,
        listener: PostListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post
        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when(menuItem.itemId) {
                        R.id.remove -> {
                            listener.clickedDelete(post)
                            true
                        }
                        R.id.update -> {
                            listener.clickUpdate(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.shares.setOnClickListener {
                listener.clickedShare(post)
                Utils.figures(post.share)
            }
            binding.video.setOnClickListener {
                  listener.clickVideo(post.video)
            }
            binding.link.setOnClickListener {
                listener.clickVideo(post.video)
            }
            binding.likes.setOnClickListener {
                listener.clickedLike(post)
                Utils.figures(post.like)
            }
            binding.options.setOnClickListener { popupMenu.show() }

            binding.root.setOnClickListener {
                listener.chooseThePost(post)
            }
        }


        fun bind(post: Post) {
            this.post = post
            with(binding) {
                authorName.text = post.author
                date.text = post.date
                text.text = post.content
                shares.text = post.share.toString()
                likes.text = post.like.toString()
                likes.isChecked = post.isLiked
                if (binding.link.text.isNullOrBlank()) {
                    binding.video.visibility = View.GONE
                    binding.link.visibility = View.GONE
                }
                else {
                    binding.video.visibility = View.VISIBLE
                    binding.link.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, postListener)
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


