package ru.netology.myapp

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.properties.Delegates

class FilePreferenceRepository(
    private val application: Application
) : PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    private val prefs = application.getSharedPreferences(
        "repository", Context.MODE_PRIVATE
    )
    private var nextId: Long by Delegates.observable(
        prefs.getLong(NEXT_ID_KEY, 0L)
    ) {_, _, newValue ->
        prefs.edit { putLong(NEXT_ID_KEY, newValue) }
    }

    private var posts
        get() = checkNotNull(data.value) {
            "NOT NULL"
        }
        set(value) {
            application.openFileOutput(FILE_NAME, Context.MODE_PRIVATE
            ).bufferedWriter().use {
                it.write(gson.toJson(value))
            }
            data.value = value
        }

    private val data: MutableLiveData<List<Post>>

    init {
        val postsFile = application.filesDir.resolve(FILE_NAME)
        val posts: List<Post> = if (postsFile.exists()) {
          val inputStream = application.openFileInput(FILE_NAME)
          val reader = inputStream.bufferedReader()
          reader.use {gson.fromJson(it, type) }
        } else emptyList()
        data = MutableLiveData(posts)
    }

    override fun getAll(): LiveData<List<Post>> = data
    override fun like(postId: Long) {
        posts = posts.map { post ->
            if (postId == post.id && !post.isLiked) post.copy(isLiked = !post.isLiked, like = post.like+1)
            else if(postId == post.id && post.isLiked) post.copy(isLiked = !post.isLiked, like = post.like-1)
            else post
        }
    }

    override fun share(postId: Long) {
        posts = posts.map { post ->
            if (postId == post.id) post.copy(share = post.share+1) else post
        }
    }

    override fun delete(postId: Long) {
        posts = posts.filterNot { it.id == postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert (post) else update(post)
    }

    private fun update(post: Post) {
        posts = posts.map {
            if(it.id == post.id) post else it
        }
    }

    override fun checkPost(post: Post) {
        posts = posts.map {
            if(it.id == post.id) post else return
        }
    }

    private fun insert(post: Post) {
        posts = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }
    private companion object {
        const val NEXT_ID_KEY = "nextID"
        const val FILE_NAME = "posts.json"
    }
}



