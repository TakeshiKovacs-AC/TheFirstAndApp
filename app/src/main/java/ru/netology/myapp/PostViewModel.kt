package ru.netology.myapp

import androidx.lifecycle.ViewModel

class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryImpl()
    val data = repository.getAll()
    fun clickedLike(post: Post) = repository.like(post.id)
    fun clickedShare(post: Post) = repository.share(post.id)
}