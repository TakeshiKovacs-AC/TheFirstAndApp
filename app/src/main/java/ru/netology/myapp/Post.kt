package ru.netology.myapp

data class Post (
    val author: String,
    val date: String,
    val content: String,
    val id: Long,
    var like: Int,
    var share: Int,
    val isLiked: Boolean = false
        )
