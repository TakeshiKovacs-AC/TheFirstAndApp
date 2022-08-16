package ru.netology.myapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryImpl : PostRepository {

    private var nextId = GENERATION_POSTS_AMOUNT.toLong()

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    private val data: MutableLiveData<List<Post>>

    init {
        val initialPosts = List(GENERATION_POSTS_AMOUNT) { index ->
            Post(
                author = "Anton",
                date = "23.06.2022",
                content = "Down the river drift an axe," + "\n"
                        + "From the town of Byron," + "\n"
                        + "Let it float by itself," + "\n"
                        + "Fucking piece of iron!",
                id = index + 1L,
                like = 0,
                share = 0,
                video = null
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

    override fun delete(postId: Long) {
        data.value = posts.filterNot { it.id == postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert (post) else update(post)
    }

    override fun play(post: Post) {
           if ((post.video).isNullOrBlank()) return
            else (post.video)
        }


    private fun update(post: Post) {
        data.value = posts.map {
            if(it.id == post.id) post else it
        }
    }

    private fun insert(post: Post) {
        data.value = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }
    private companion object {
        const val GENERATION_POSTS_AMOUNT = 1000
    }
}



