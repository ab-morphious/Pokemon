package com.daabsoft.pokemon.domain.models

import com.daabsoft.pokemon.core.Constants.IMAGE_BASE_URL
import com.daabsoft.pokemon.data.local.entity.PokemonEntity
import java.lang.Exception

data class Pokemon(
    val name: String,
    val url: String
) {
    private fun getId(): Int {
        val splitPath = url.split("/")
        return try {
            splitPath[splitPath.size - 2].toInt()
        } catch (e: Exception) {
            -1
        }
    }

    fun getImageMapped(): String = "${IMAGE_BASE_URL}${getId()}.png"

    fun toEntitiy(page: Int) = PokemonEntity(
        page = page,
        name = name,
        url = url
    )
}
