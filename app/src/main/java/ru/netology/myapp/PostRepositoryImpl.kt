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
        post = post.copy(isLiked = !post.isLiked)
        if (post.isLiked) post.like++ else post.like--
        Utils.figures(post.like)
        data.value = post
    }

    override fun share() {
        post.share++
        Utils.figures(post.share)
        data.value = post
    }


}



