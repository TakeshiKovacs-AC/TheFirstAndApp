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
import kotlinx.serialization.json.JsonNull
import ru.netology.myapp.FeedFragment.Companion.textArg
import ru.netology.myapp.databinding.FragmentOnePostBinding

class OnePostFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        val binding = FragmentOnePostBinding.inflate(
            inflater,
            container,
            false
        )
        val viewModel: PostViewModel by viewModels(
            ownerProducer = ::requireParentFragment
        )

        val viewHolder = PostsAdapter.ViewHolder(binding.onePost, viewModel)

        viewModel.data.observe(viewLifecycleOwner) { postsList ->
            postsList.forEach {
                if (it.id == arguments?.longArg)
                    viewHolder.bind(it)
            }
        }

        viewModel.deleteById.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
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
            findNavController().navigate(R.id.onePostFragment_to_postContentFragment,
                Bundle().apply
                { textArg = JsonNull.content })
        }

          return binding.root
    }

    companion object {
        var Bundle.longArg: Long by LongArguments
    }
}
