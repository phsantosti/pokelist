package com.raywenderlich.pokelist

import com.raywenderlich.pokelist.shared.ApplicationDispatcher
import com.raywenderlich.pokelist.shared.Image
import com.raywenderlich.pokelist.shared.toNativeImage
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json

class PokeApi {

    private val httpClient = HttpClient()

    fun getPokemonList(success: (List<PokemonEntry>) -> Unit, failure: (Throwable?) -> Unit){
        GlobalScope.launch(ApplicationDispatcher){
            try {
                val url = "https://pokeapi.co/api/v2/pokedex/kanto/"
                val json = httpClient.get<String>(url)
                Json.nonstrict.parse(Pokedex.serializer(), json)
                    .pokemon_entries
                    .also(success)
            } catch (ex: Exception){
                failure(ex)
            }
        }
    }

    fun getPokemonSprite(pokemonId: Int, success: (Image?) -> Unit, failure: (Throwable?) -> Unit){
        GlobalScope.launch(ApplicationDispatcher){
            try {
                val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"
                httpClient.get<ByteArray>(url)
                    .toNativeImage()
                    .also(success)
            } catch (ex: Exception){
                failure(ex)
            }
        }
    }

    fun getPokemonInfo(pokemonId: Int, success: (String) -> Unit, failure: (Throwable?) -> Unit){
        GlobalScope.launch(ApplicationDispatcher){

            try {
                val url = "https://pokeapi.co/api/v2/pokemon-species/$pokemonId/"
                val json = httpClient.get<String>(url)
                Json.nonstrict.parse(FlavorTextEntries.serializer(), json)
                    .flavor_text_entries
                    .asSequence()
                    .filter { it.version.name == "red" || it.version.name == "blue" || it.version.name == "yellow" }
                    .filter { it.language.name == "en" }
                    .toList()
                    .firstOrNull()
                    ?.flavor_text
                    ?.also(success)
            } catch (ex: Exception){
                failure(ex)
            }
        }
    }
}