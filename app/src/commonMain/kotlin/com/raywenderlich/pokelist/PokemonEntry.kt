package com.raywenderlich.pokelist

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class PokemonEntry(
    val entry_number: Int,
    val pokemon_species: Pokemon
) {
    @Transient
    val label: String
        get() {
            val name = pokemon_species.name.toUpperCase()
            val id = entry_number.padding(3)
            return "N°$id\t\t$name"
        }
}