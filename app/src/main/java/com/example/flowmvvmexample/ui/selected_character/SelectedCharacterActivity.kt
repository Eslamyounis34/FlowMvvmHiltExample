package com.example.flowmvvmexample.ui.selected_character

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.flowmvvmexample.R
import com.example.flowmvvmexample.common.utils.OnCharacterClick
import com.example.flowmvvmexample.common.utils.Resource
import com.example.flowmvvmexample.databinding.ActivitySelectedCharacterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectedCharacterActivity : AppCompatActivity() {
    lateinit var binding: ActivitySelectedCharacterBinding
    lateinit var characterUrl: String
    val viewModel: SelectedCharacterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
        subscribeToCharacterResponse()


    }

    private fun initUi() {
        binding = ActivitySelectedCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var id = intent.extras!!.getInt("characterId")
        lifecycleScope.launch {
            viewModel.getCharacterData(id)
        }
        binding.apply {
            shareIcon.setOnClickListener {
                shareCharacter()
            }
        }
    }

    private fun subscribeToCharacterResponse() {
        lifecycleScope.launch {
            viewModel.liveCharacterData.collect { it ->
                Log.e("testResponse", it.data.toString())
                when (it) {
                    is Resource.Loading -> {
                        binding.progressPar.visibility = View.VISIBLE
                    }

                    is Resource.DataError -> {
                        binding.progressPar.visibility = View.GONE

                    }

                    is Resource.Success -> {
                        binding.progressPar.visibility = View.GONE
                        characterUrl = it.data!!.url
                        binding.apply {
                            characterName.text = it.data!!.name
                            characterImage.load(it.data.image) {
                                crossfade(true)
                                crossfade(1000)
                            }

                        }

                    }
                }
            }
        }
    }

    private fun shareCharacter() {
        val url = characterUrl
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this website!")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }


}