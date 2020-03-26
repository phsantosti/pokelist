package com.raywenderlich.pokelist

import kotlinx.serialization.Serializable

@Serializable
data class FlavorTextEntries(
    val flavor_text_entries: List<FlavorText>
)

@Serializable
data class FlavorText(
    val flavor_text: String,
    val language: FlavorLanguage,
    val version: FlavorVersion
)

@Serializable
data class FlavorLanguage(
    val name: String
)

@Serializable
data class FlavorVersion(
    val name: String
)

