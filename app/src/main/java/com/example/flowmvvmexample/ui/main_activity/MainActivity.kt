package com.example.flowmvvmexample.ui.main_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.flowmvvmexample.common.utils.OnCharacterClick
import com.example.flowmvvmexample.databinding.ActivityMainBinding
import com.example.flowmvvmexample.common.utils.Resource
import com.example.flowmvvmexample.ui.selected_character.SelectedCharacterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , OnCharacterClick {
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
            characterAdapter.setOnItemClickListener(this@MainActivity)
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

    override fun onClick(id: Int) {
        var intent = Intent(this,SelectedCharacterActivity::class.java)
        intent.putExtra("characterId", id)
        startActivity(intent)
      //  Toast.makeText(this, "$id", Toast.LENGTH_SHORT).show()
    }
}