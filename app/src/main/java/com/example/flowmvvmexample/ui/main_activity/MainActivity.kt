package com.example.flowmvvmexample.ui.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.flowmvvmexample.databinding.ActivityMainBinding
import com.example.flowmvvmexample.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var characterAdapter: CharacterAdapter
    val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecycler()
        loadData()
    }

    private fun setupRecycler() {
        characterAdapter = CharacterAdapter()
        binding.characterRecycler.apply {
            adapter = characterAdapter
            setHasFixedSize(true)

        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.listData.collect {
                characterAdapter.submitData(it)
                Log.e("testReponse", it.toString())
            }
        }
        lifecycleScope.launch{
            viewModel.isLoading.collect { loading ->
                when(loading){
                    is Resource.DataError -> {
                        Toast.makeText(this@MainActivity, "SomeThing Error", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading ->{
                        binding.progressPar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressPar.visibility = View.GONE
                    }
                }
            }
        }

    }
}