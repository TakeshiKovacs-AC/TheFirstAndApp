package ru.netology.myapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import ru.netology.myapp.databinding.ActivityPostContentBinding

class PostContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPostContentBinding.inflate(layoutInflater)

        val postIntent = intent ?: return

        val postContent = postIntent.getStringExtra(Intent.EXTRA_TEXT)
        binding.edit.setText(postContent)
//        val intentPostContent = postContent?.toEditable()
//        binding.edit.text = intentPostContent


        setContentView(binding.root)
        binding.edit.requestFocus()
        binding.okButton.setOnClickListener {
            val newIntent = Intent()
            val editText = binding.edit.text
            if (editText.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, newIntent)
                Toast.makeText(
                    this@PostContentActivity, "Пустая строка поста", Toast.LENGTH_SHORT
                ).show()
            } else {
                val content = editText.toString()
                newIntent.putExtra(RESULT_KEY, content)
                setResult(Activity.RESULT_OK, newIntent)
            }
            finish()
        }
    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}