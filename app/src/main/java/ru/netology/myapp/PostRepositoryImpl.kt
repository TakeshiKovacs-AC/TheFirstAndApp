package ru.netology.myapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryImpl : PostRepository {

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    private val data: MutableLiveData<List<Post>>

    init {
        val initialPosts = List(1000) { index ->
            Post(
                author = "Anton",
                date = "23.06.2022",
                content = "I would definitely say there's a different type of " +
                        "camaraderie for guys whose dads played in the NBA,\" said Pippen Jr., 21, " +
                        "who went undrafted out of Vanderbilt and signed a two-way contract with L.A. " +
                        "last week. \"Because going through this whole process since we were little " +
                        "kids, there's always a different type of pressure on us, I would say. " +
                        "A different type of expectation. So I tip my hat to all those guys, because " +
                        "playing this game when your father is so-and-so is a different type of pressure " +
                        "to play at",
                id = index + 1L,
                like = 0,
                share = 0
            )
        }
        data = MutableLiveData(initialPosts)
    }

    override fun getAll(): LiveData<List<Post>> = data
    override fun like(postId: Long) {
        posts = posts.map { post ->
            if (postId == post.id && !post.isLiked) post.copy(isLiked = !post.isLiked, like = post.like+1)
            else if(postId == post.id && post.isLiked) post.copy(isLiked = !post.isLiked, like = post.like-1)
            else post
        }
    }

    override fun share(postId: Long) {
        posts = posts.map { post ->
            if (postId == post.id) post.copy(share = post.share+1) else post
        }
    }
}



