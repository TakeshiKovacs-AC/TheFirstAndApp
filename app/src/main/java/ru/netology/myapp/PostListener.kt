package ru.netology.myapp

interface PostListener {
    fun clickedLike(post: Post)
    fun clickedShare(post: Post)
    fun clickedDelete(post: Post)
    fun clickUpdate(post: Post)
    fun clickVideo(video: String?)
}