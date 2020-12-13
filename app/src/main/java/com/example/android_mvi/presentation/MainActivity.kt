package com.example.android_mvi.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_mvi.Inent.MainIntent
import com.example.android_mvi.R
import com.example.android_mvi.data.api.ApiHelperImpl
import com.example.android_mvi.data.api.RetrofitBuilder
import com.example.android_mvi.data.model.Post
import com.example.android_mvi.presentation.adapter.MainAdapter
import com.example.android_mvi.util.ViewModelFactory
import com.example.android_mvi.viewstate.MainState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {


    private lateinit var mainViewModel: MainViewModel
    private  var adapter = MainAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val toolbar = supportActionBar
        toolbar?.title = "Android MVI"

        setupUi()
        setupViewModel()
        observeViewModel()
        setupUiClickListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val currentTheme = AppCompatDelegate.getDefaultNightMode()
        Log.d("MainActivity ","Theme $currentTheme")
        return when(item.itemId){
            R.id.dark_mode -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                return true
            }

            R.id.day_mode -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                return true
            }

            else -> {
                 super.onOptionsItemSelected(item)
            }
        }

    }

    private fun setupUiClickListener() {
        btnFetch.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchPost)
            }
        }

    }


    private fun setupUi() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.run {
            addItemDecoration(DividerItemDecoration(
                recyclerView.context,(recyclerView.layoutManager as LinearLayoutManager).orientation
            ))
        }
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService)
            ))
            .get(MainViewModel::class.java)
    }

    private fun observeViewModel(){
        lifecycleScope.launch {
            mainViewModel.state
                .collect {
                    when(it){
                        is MainState.Idle -> {}

                        is MainState.Loading -> {
                            btnFetch.visibility = View.GONE
                            loading.visibility = View.VISIBLE
                        }

                        is MainState.Posts -> { renderUi(it.posts) }

                        is MainState.Error -> {
                            loading.visibility = View.GONE
                            btnFetch.visibility = View.VISIBLE
                            Toast.makeText(this@MainActivity,it.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

    private fun renderUi(posts: List<Post>) {
        loading.visibility = View.GONE
        btnFetch.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        adapter.setNewData(posts)
    }
}