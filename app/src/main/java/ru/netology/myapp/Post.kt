package ru.netology.myapp

data class Post (
    val author: String,
    val date: String,
    val content: String,
    val id: Long,
    val like: Int = 0,
    val share: Int = 0,
    val isLiked: Boolean = false,
    val video: String?
        )
