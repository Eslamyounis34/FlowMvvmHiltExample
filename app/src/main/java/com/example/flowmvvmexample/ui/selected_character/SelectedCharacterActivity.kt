package com.example.flowmvvmexample.ui.selected_character

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.flowmvvmexample.R
import com.example.flowmvvmexample.common.utils.OnCharacterClick
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectedCharacterActivity : AppCompatActivity() {
    val viewModel: SelectedCharacterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_character)

        initUi()
        lifecycleScope.launch {
            viewModel.liveCharacterData.collect { it ->
                Log.e("testResponse", it.data.toString())
            }
        }

    }

    private fun initUi() {
        var id = intent.extras!!.getInt("characterId")
        lifecycleScope.launch {
            viewModel.getCharacterData(id)
        }


    }


}