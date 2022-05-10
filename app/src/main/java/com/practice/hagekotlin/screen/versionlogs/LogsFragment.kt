package com.practice.hagekotlin.screen.versionlogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.practice.hagekotlin.R
import com.practice.hagekotlin.databinding.ScreenComposeBinding
import com.practice.hagekotlin.screen.versionlogs.LogsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogsFragment : Fragment() {

    private lateinit var binding: ScreenComposeBinding

    val viewModel: LogsViewModel by viewModel()

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

        viewModel.logs.observe(viewLifecycleOwner) { contents ->

            binding.composeWindow.setContent {

                LazyColumn {
                    items(contents) {
                        Text(
                            text = it.text,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_text_row))
                        )
                    }
                }
            }
        }
    }
}

