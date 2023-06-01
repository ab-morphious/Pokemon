package com.daabsoft.pokemon.domain.models

import com.daabsoft.pokemon.core.Constants.IMAGE_BASE_URL

data class Pokemon(
    val name: String,
    val url: String,
    var imageUrl: String = ""
) {
    private fun getId(): Int {
        val splitPath = url.split("/")
        return splitPath[splitPath.size - 2].toInt()
    }

    fun getImageMapped(): String = "${IMAGE_BASE_URL}${getId()}.png"
}
