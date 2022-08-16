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


        viewModel.playVideo.observe(this) { video ->
           val videoIntent = Intent().apply {
               action = Intent.ACTION_SEND
               type = "video/*"
               putExtra(Intent.ACTION_VIEW, Uri.parse(video))
           }
            val playVideoIntent = Intent.createChooser(videoIntent, "Проигрываем видео")
            startActivity(playVideoIntent)
        }

        viewModel.shareEvent.observe(this) { post ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, post.content)
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
            viewModel.clickUpdate(postContent)
        }

        viewModel.navigateEvent.observe(this) {
            postBuildActivityLauncher.launch()
        }

        viewModel.editEvent.observe(this) {
            postEditActivityLauncher.launch(it)
        }
    }

//        binding.saveButton.setOnClickListener {
//            with(binding.addedText) {
//                val content = binding.addedText.text.toString()
//                if (text.isNullOrBlank()) {
//                    Toast.makeText(
//                        this@MainActivity, "Пустая строка поста", Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnClickListener
//                } else viewModel.clickedSave(content)
//
//                clearFocus()
//                hideKeyboard()
//                binding.fab.visibility = View.VISIBLE
//            }
//        }
//        binding.addedText.setOnClickListener {
//            binding.buttonsGroup.visibility = View.VISIBLE
//            binding.fab.visibility = View.GONE
//        }
//
//        binding.cancelEditButton.setOnClickListener {
//            with(binding.addedText) {
//                if (binding.addedText.text.isNullOrBlank()) {
//                    binding.buttonsGroup.visibility = View.GONE
//                    hideKeyboard()
//                    binding.fab.visibility = View.VISIBLE
//                }
//                else binding.addedText.text = null
//            }
//        }

//        viewModel.thisPost.observe(this) { thisPost ->
//            binding.addedText.setText(thisPost?.content)
//            binding.buttonsGroup.visibility = View.GONE
//        }
}


