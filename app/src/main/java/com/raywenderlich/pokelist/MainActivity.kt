package com.raywenderlich.pokelist

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlin.coroutines.CoroutineContext
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar

class MainActivity: AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    private lateinit var api: PokeApi

    override val coroutineContext: CoroutineContext
        get() = job + Main

    private val adapter = PokeListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = Job()

        api = PokeApi()

        setupRecyclerView()

        loadList()
    }

    private fun loadList(){
        api.getPokemonList (
            success = { launch(Main) { adapter.updateData(it) } },
            failure = ::handleError
        )
    }

    private fun setupRecyclerView() {
        adapter.onClick = {
            loadPokemon(it.entry_number)
        }

        pokelist_rv.layoutManager = LinearLayoutManager(this)
        pokelist_rv.adapter = adapter
        pokelist_rv.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    private fun loadPokemon(id: Int){
        api.getPokemonSprite(id,
            success = { launch(Main) { pokemon_sprite.setImageBitmap(it) } },
            failure = ::handleError
        )

        api.getPokemonInfo(id,
            success = { launch(Main) { pokemon_info.text = it } },
            failure = ::handleError
        )
    }

    private fun handleError(ex: Throwable?){
        ex?.printStackTrace()
        launch (Main){
            val msg = ex?.message ?: "Unknown error"
            Snackbar.make(root_view, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry") { loadList() }
                .show()
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
