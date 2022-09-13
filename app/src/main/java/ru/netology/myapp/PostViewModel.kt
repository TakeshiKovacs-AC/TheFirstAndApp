package ru.netology.myapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class PostViewModel (
    application: Application
        ): AndroidViewModel(application), PostListener {
    private val repository: PostRepository = FilePreferenceRepository(application)
    val data = repository.getAll()
    override fun clickedLike(post: Post) = repository.like(post.id)
    override fun clickedShare(post: Post) {
        repository.share(post.id)
        shareEvent.value = post
    }

    override fun clickedDelete(post: Post) = repository.delete(post.id)
    override fun clickUpdate(post: Post) {
        thisPost.value = post
        editEvent.value = post.content
    }

    override fun clickVideo(video: String?) {
        playVideo.value = video
    }

    val shareEvent = SingleLiveEvent<Post>()
    val editEvent = SingleLiveEvent<String?>()
    val playVideo = SingleLiveEvent<String?>()
    val checkById = SingleLiveEvent<Long?>()

    fun clickedSave(content: String) {
        if (content.isBlank()) return
        val post = thisPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Anton",
            content = content,
            date = "Present days",
            video = null
        )
        repository.save(post)
        thisPost.value = null
    }

    override fun choosePost (post: Post) {

    }

    val thisPost = MutableLiveData<Post?>(null)
}