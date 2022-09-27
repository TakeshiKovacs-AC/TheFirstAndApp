package ru.netology.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import ru.netology.myapp.FeedFragment.Companion.textArg
import ru.netology.myapp.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent ?: return
        if (intent.action != Intent.ACTION_SEND) return

        val text = intent.getStringExtra(Intent.EXTRA_TEXT)

        if (text.isNullOrBlank()) {
            Snackbar.make(binding.root, (R.string.noText), Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok) { finish() }
                .show()
        }

//        обработка помещения текста интента во фрагмент добавления поста
            val fragmentFeed =
                supportFragmentManager.findFragmentById(R.id.navigation_fragment) as NavHostFragment
            fragmentFeed.navController
                .navigate(
                    R.id.feedFragment_to_postContentFragment,
                    Bundle().apply { textArg = text })

            val fragmentOne =
                supportFragmentManager.findFragmentById(R.id.navigation_fragment) as NavHostFragment
            fragmentOne.navController
                .navigate(
                    R.id.onePostFragment_to_postContentFragment,
                    Bundle().apply { textArg = text })
        }
    }

