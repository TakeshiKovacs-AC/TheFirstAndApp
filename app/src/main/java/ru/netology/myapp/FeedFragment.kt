package ru.netology.myapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.serialization.json.JsonNull.content
import ru.netology.myapp.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )

        val viewModel: PostViewModel by viewModels(
            ownerProducer = ::requireParentFragment
        )
        val adapter = PostsAdapter(viewModel)

        binding.postsRecycler.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts: List<Post> ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.feedFragment_to_postContentFragment)
        }

        viewModel.playVideo.observe(viewLifecycleOwner) {
           val videoIntent = Intent().apply {
               action = Intent.ACTION_VIEW
               data = Uri.parse(it)
           }
            val playVideoIntent = Intent.createChooser(videoIntent, "Проигрываем видео")
            startActivity(playVideoIntent)
        }

        viewModel.shareEvent.observe(viewLifecycleOwner) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, it.content)
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.editEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.feedFragment_to_postContentFragment,
            Bundle().apply
             { textArg = content })
        }

        viewModel.checkById.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.feedFragment_to_onePostFragment,
                Bundle().apply {
                    it?.let {longArg = it}
                })
        }

        return binding.root
    }

    companion object {
        private const val TEXT_KEY = "TEXT_KEY"
        var Bundle.textArg: String?
            set(value) = putString(TEXT_KEY, value)
            get() = getString(TEXT_KEY)

//        private const val POST_KEY = "POST_KEY"
        var Bundle.longArg: Long by LongArguments
//            set(value) = putLong(POST_KEY, value)
//            get() = getLong(POST_KEY)
    }
}


