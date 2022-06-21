package ru.netology.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.myapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var likesQuantity = 999
        var shareQuantity = 2689432
        val post = Post(
            author = "Anton",
            date = "13.06.2022",
            content = "Welcome to Netology, the best online education platform",
            id = 647892L,
        )


        binding.shares.setOnClickListener {
            shareQuantity++
            binding.sharesCount.text = figures(shareQuantity)
            }


        with(binding) {
            authorName.text = post.author
            date.text = post.date
            text.text = post.content
            if (post.isLiked) {
                likes.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
            likes.setOnClickListener {
                post.isLiked = !post.isLiked
                likes.setImageResource(
                    if (post.isLiked) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                )
                if (post.isLiked)
                    likesQuantity += 1
                else likesQuantity -= 1
                likesCount.text = figures(likesQuantity)
            }
        }
    }
    private fun figures(quantityContainer: Int): String {
        return when (quantityContainer) {
            in 0..999 -> quantityContainer.toString()
            in 1000..9999 -> String.format("%.1f", (quantityContainer / 1000.0)) + "K"
            in 10_000..999_999 -> "${(quantityContainer / 1000)}K"
            in 1_000_000..999_999_999 -> String.format("%.1f", (quantityContainer / 1_000_000.0)) + "M"
            else -> "Недопустимое число"
        }
    }
}