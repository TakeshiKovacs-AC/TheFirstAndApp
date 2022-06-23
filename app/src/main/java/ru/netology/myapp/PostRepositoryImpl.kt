package ru.netology.myapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryImpl: PostRepository {

      var post = Post(
        author = "Anton",
        date = "23.06.2022",
        content = "Welcome to Netology, the best online education platform",
        id = 647892L,
        like = 3,
        share = 5
    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun like() {
        post = post.copy(isLiked = !post.isLiked, like = post.like)
        data.value = post
    }

    fun figures(quantityContainer: Int): String {
        return when (quantityContainer) {
            in 0..999 -> quantityContainer.toString()
            in 1000..9999 -> String.format("%.1f", (quantityContainer / 1000.0)) + "K"
            in 10_000..999_999 -> "${(quantityContainer / 1000)}K"
            in 1_000_000..999_999_999 -> String.format("%.1f", (quantityContainer / 1_000_000.0)) + "M"
            else -> "Недопустимое число"
        }
    }
}



