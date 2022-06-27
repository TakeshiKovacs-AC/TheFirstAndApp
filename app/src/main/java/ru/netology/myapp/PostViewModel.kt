package ru.netology.myapp

import androidx.lifecycle.ViewModel

class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryImpl()
    val data = repository.get()
    fun clickedLike() = repository.like()
    fun clickedShare() = repository.share()
}