package ru.netology.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.myapp.databinding.FragmentPostContentBinding

class PostContentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPostContentBinding.inflate(
            inflater,
            container,
            false
        ).also { binding ->
        val viewModel: PostViewModel by viewModels(
            ownerProducer = ::requireParentFragment)

        binding.edit.setText(arguments?.textArg)
//        arguments?.textArg.let(binding.edit::setText)

        binding.edit.requestFocus()
        binding.okButton.setOnClickListener {
            val editText = binding.edit.text
            if (editText.isNullOrBlank()) {
                Toast.makeText(activity, "Отсутствует текст поста!", Toast.LENGTH_SHORT).
                    show()
                findNavController().navigateUp()
            } else {
                val content = editText.toString()
                viewModel.clickedSave(content)
                Toast.makeText(activity, "Добавлен новый пост!", Toast.LENGTH_SHORT).
                show()
            }
            findNavController().navigateUp()
        }
    }.root

    companion object {
        var Bundle.textArg: String? by StringArguments
    }
}

