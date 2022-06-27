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
            }
        }
        binding.shares.setOnClickListener {
            viewModel.clickedShare()
        }
        binding.likes.setOnClickListener {
            viewModel.clickedLike()
        }
    }
}

