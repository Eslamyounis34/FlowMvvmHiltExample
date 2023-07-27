package com.example.flowmvvmexample.ui.main_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.flowmvvmexample.common.utils.OnCharacterClick
import com.example.flowmvvmexample.databinding.ActivityMainBinding
import com.example.flowmvvmexample.common.utils.Resource
import com.example.flowmvvmexample.common.utils.loadingAlert
import com.example.flowmvvmexample.ui.selected_character.SelectedCharacterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnCharacterClick {
    lateinit var binding: ActivityMainBinding
    lateinit var characterAdapter: CharacterAdapter
    lateinit var loadingAlert: SweetAlertDialog
    val viewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecycler()
        loadData()
       // observeLoadingState()


    }

    private fun setupRecycler() {
        characterAdapter = CharacterAdapter()
        binding.characterRecycler.apply {
            adapter = characterAdapter
            characterAdapter.setOnItemClickListener(this@MainActivity)
            setHasFixedSize(true)

        }
    }

//    private fun observeLoadingState(){
//
//        viewModel.isLoading.observe(this, Observer { loading ->
//            Log.e("observing", "begin")
//
//            if (loading) {
//                Log.e("testLoading", "loading")
//                loadingAlert = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
//                sweetLoadingAlert("loading")
////                binding.progressPar.visibility = View.VISIBLE
//            } else {
//                loadingAlert.dismissWithAnimation()
//                Log.e("testLoading", "success")
////                binding.progressPar.visibility = View.GONE
//            }
//        })
//    }

    private fun loadData() {
        Log.e("testFun", "begin")
        lifecycleScope.launch {
            viewModel.listData.collect {
                characterAdapter.submitData(it)
                Log.e("testReponse", it.toString())

            }
        }

    }

    override fun onClick(id: Int) {
        var intent = Intent(this, SelectedCharacterActivity::class.java)
        intent.putExtra("characterId", id)
        startActivity(intent)
        //  Toast.makeText(this, "$id", Toast.LENGTH_SHORT).show()
    }

    private fun sweetLoadingAlert(loadingMsg: String) {
        loadingAlert.setTitleText(loadingMsg)
        loadingAlert.setCancelable(false)
        loadingAlert.show()
    }
}