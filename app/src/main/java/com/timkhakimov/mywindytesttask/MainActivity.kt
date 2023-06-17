package com.timkhakimov.mywindytesttask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.timkhakimov.mywindytesttask.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: SumViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeData()
        setListeners()
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.resultFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    binding.resultTextView.text = if (it == 0) {
                        ""
                    } else {
                        it.toString()
                    }
                }
        }
    }

    private fun setListeners() {
        binding.startSumButton.setOnClickListener {
            viewModel.startSummation(
                binding.inputValueEditText.text.toString().toIntOrNull() ?: 0
            )
        }
    }
}