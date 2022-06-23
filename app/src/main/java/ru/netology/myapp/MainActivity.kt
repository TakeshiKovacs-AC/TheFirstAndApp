package ru.netology.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.myapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val postRepImpl = PostRepositoryImpl()

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                authorName.text = post.author
                date.text = post.date
                text.text = post.content
                sharesCount.text = post.share.toString()
                likesCount.text = post.like.toString()
                likes.setImageResource(
                    if (post.isLiked) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                )
                if (post.isLiked) post.like++ else post.like--
                binding.likesCount.text = postRepImpl.figures(post.like)

                binding.shares.setOnClickListener {
                    post.share++
                    sharesCount.text = postRepImpl.figures(post.share)
                }
            }
        }
        binding.likes.setOnClickListener {
            viewModel.clickedLike()
        }
    }
}