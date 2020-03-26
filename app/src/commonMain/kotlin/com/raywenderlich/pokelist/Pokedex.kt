package com.raywenderlich.pokelist

import kotlinx.serialization.Serializable

@Serializable
data class Pokedex(
    val pokemon_entries: List<PokemonEntry>
)