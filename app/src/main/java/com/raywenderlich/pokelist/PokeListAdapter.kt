package com.raywenderlich.pokelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PokeListAdapter: RecyclerView.Adapter<PokeListAdapter.Holder>(){

    private val pokeList: ArrayList<PokemonEntry> = arrayListOf()

    var onClick: (PokemonEntry) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, null)
            .let { Holder(it as TextView) }

    override fun getItemCount(): Int =
        pokeList.count()

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(pokeList[position])

    fun updateData(list: List<PokemonEntry>){
        pokeList.clear()
        pokeList.addAll(list)
        notifyDataSetChanged()
    }

    inner class Holder(private val view: TextView): RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onClick(pokeList[adapterPosition])
        }

        fun bind(pokemon: PokemonEntry){
            view.text = pokemon.label
        }
    }
}