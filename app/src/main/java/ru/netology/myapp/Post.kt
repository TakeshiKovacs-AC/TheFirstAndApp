package ru.netology.myapp

import kotlinx.serialization.Serializable

@Serializable
data class Post (
    val author: String,
    val date: String,
    val content: String,
    var id: Long,
    val like: Int = 0,
    val share: Int = 0,
    val isLiked: Boolean = false,
    val video: String?
        )
