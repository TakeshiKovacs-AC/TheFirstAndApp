package ru.netology.myapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import ru.netology.myapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(viewModel)

        binding.postsRecycler.adapter = adapter
        viewModel.data.observe(this) { posts: List<Post> ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            viewModel.clickAdd()
        }

        viewModel.playVideo.observe(this) {
           val videoIntent = Intent().apply {
               action = Intent.ACTION_VIEW
               data = Uri.parse(it)
           }
            val playVideoIntent = Intent.createChooser(videoIntent, "Проигрываем видео")
            startActivity(playVideoIntent)
        }

        viewModel.shareEvent.observe(this) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, it.content)
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }


        val postBuildActivityLauncher = registerForActivityResult(
            ResultContractSave) { postContent ->
            postContent ?: return@registerForActivityResult
            viewModel.clickedSave(postContent)
        }

        val postEditActivityLauncher = registerForActivityResult(
            ResultContractUpdate) { postContent ->
            postContent ?: return@registerForActivityResult
            viewModel.clickedSave(postContent)
        }

        viewModel.navigateEvent.observe(this) {
            postBuildActivityLauncher.launch()
        }

        viewModel.editEvent.observe(this) {
            postEditActivityLauncher.launch(it)
        }
    }
}


