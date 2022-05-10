package com.practice.hagekotlin.screen.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.practice.hagekotlin.R
import com.practice.hagekotlin.databinding.ScreenComposeBinding
import com.practice.hagekotlin.screen.contents.ContentsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryFragment : Fragment() {

    private lateinit var binding: ScreenComposeBinding

    val viewModel: CategoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScreenComposeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.categories.observe(viewLifecycleOwner) { categories ->

            binding.composeWindow.setContent {
                CategoryScreen(categories = categories) { categoryId, categoryName ->
                    startActivity(Intent(requireActivity(), ContentsActivity::class.java).apply {
                        putExtra(ContentsActivity.KEY_CATEGORY_NAME, categoryName)
                        putExtra(ContentsActivity.KEY_CATEGORY_ID, categoryId)
                    })
                }
            }
        }
    }
}


