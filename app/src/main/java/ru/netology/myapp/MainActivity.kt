package ru.netology.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        binding.saveButton.setOnClickListener {
            with(binding.addedText) {
                val content = binding.addedText.text.toString()
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity, "Пустая строка поста", Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else viewModel.clickedSave(content)

                clearFocus()
                hideKeyboard()
            }
        }

        binding.addedText.setOnClickListener {
            binding.buttonsGroup.visibility = View.VISIBLE
        }

        binding.cancelEditButton.setOnClickListener {
            with(binding.addedText) {
                if (binding.addedText.text.isNullOrBlank()) {
                    binding.buttonsGroup.visibility = View.GONE
                    hideKeyboard()
                }
                else binding.addedText.text = null
            }
        }

        viewModel.thisPost.observe(this) { thisPost ->
            binding.addedText.setText(thisPost?.content)
            binding.buttonsGroup.visibility = View.GONE
        }
    }
}


