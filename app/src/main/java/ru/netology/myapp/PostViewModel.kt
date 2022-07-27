package ru.netology.myapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostViewModel: ViewModel(), PostListener {
    private val repository: PostRepository = PostRepositoryImpl()
    val data = repository.getAll()
    override fun clickedLike(post: Post) = repository.like(post.id)
    override fun clickedShare(post: Post) = repository.share(post.id)
    override fun clickedDelete(post: Post) = repository.delete(post.id)
    override fun clickUpdate(post: Post) {
        thisPost.value = post
    }

    fun clickedSave(content: String) {
        if(content.isBlank()) return
        val post = thisPost.value?.copy(
            content = content
        ) ?: Post (
            id = PostRepository.NEW_POST_ID,
            author = "Anton",
            content = content,
            date = "Present days"
        )
        repository.save(post)
        thisPost.value = null
    }

    val thisPost = MutableLiveData<Post?>(null)
 }