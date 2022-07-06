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
        val adapter = PostsAdapter(
            clickedLike = { post ->
                viewModel.clickedLike(post)
            },
            clickedShare = { post ->
                viewModel.clickedShare(post)
            }
        )

        binding.postsRecycler.adapter = adapter
        viewModel.data.observe(this) { posts: List<Post> ->
            adapter.submitList(posts)
        }
    }
}


