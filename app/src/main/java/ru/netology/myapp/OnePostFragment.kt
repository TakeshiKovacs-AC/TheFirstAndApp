package ru.netology.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

        val postsList = viewModel.data.value
        if (postsList != null)
            for (post in postsList)
                if (post.id == arguments?.longArg)
                    viewHolder.bind(post)

          return binding.root
    }

    companion object {
        var Bundle.longArg: Long by LongArguments
    }
}
