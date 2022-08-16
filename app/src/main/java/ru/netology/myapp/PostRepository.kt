package ru.netology.myapp

import androidx.lifecycle.LiveData

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun like(postId: Long)
    fun share(postId: Long)
    fun delete(postId: Long)
    fun save(post: Post)
    fun play(post: Post)

    companion object {
        const val NEW_POST_ID = 0L
    }
}