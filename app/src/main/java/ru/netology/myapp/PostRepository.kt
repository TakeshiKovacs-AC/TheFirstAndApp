package ru.netology.myapp

import androidx.lifecycle.LiveData

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun share()
}