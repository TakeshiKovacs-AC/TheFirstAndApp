package ru.netology.myapp

import android.net.Uri

interface PostListener {
    fun clickedLike(post: Post)
    fun clickedShare(post: Post)
    fun clickedDelete(post: Post)
    fun clickUpdate(content: String)
    fun clickVideo(post: Post)
}