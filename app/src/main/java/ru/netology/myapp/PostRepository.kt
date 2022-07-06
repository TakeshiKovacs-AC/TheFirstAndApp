package ru.netology.myapp

import androidx.lifecycle.LiveData

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun like(postId: Long)
    fun share(postId: Long)
}