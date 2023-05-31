package com.daabsoft.pokemon.domain.models

data class Pokemon(
    val id: Long? = 0L,
    val name: String,
    val url: String,
    val imageUrl: String? = ""
)
