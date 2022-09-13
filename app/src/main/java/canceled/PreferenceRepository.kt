package canceled

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.netology.myapp.Post
import ru.netology.myapp.PostRepository
import kotlin.properties.Delegates

class PreferenceRepository(
    application: Application
) : PostRepository {

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
        prefs.edit {
            val postsSerialization = Json.encodeToString(value)
            putString(PREFS_KEY, postsSerialization)
        }
            data.value = value
    }
    private val data: MutableLiveData<List<Post>>

    init {
        val postsSerialization = prefs.getString(PREFS_KEY, null)
        val posts: List<Post> = if (postsSerialization != null) {
             Json.decodeFromString(postsSerialization)
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

    private fun insert(post: Post) {
        posts = listOf(
            post.copy(id = ++nextId)
        ) + posts
    }
    private companion object {
        const val NEXT_ID_KEY = "nextID"
        const val PREFS_KEY = "posts"
    }
}



