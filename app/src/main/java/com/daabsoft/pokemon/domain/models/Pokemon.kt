package com.daabsoft.pokemon.domain.models

import android.os.Parcel
import android.os.Parcelable
import com.daabsoft.pokemon.core.Constants.IMAGE_BASE_URL
import com.daabsoft.pokemon.data.local.entity.PokemonEntity
import java.lang.Exception

data class Pokemon(
    val name: String,
    val url: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pokemon> {
        override fun createFromParcel(parcel: Parcel): Pokemon {
            return Pokemon(parcel)
        }

        override fun newArray(size: Int): Array<Pokemon?> {
            return arrayOfNulls(size)
        }
    }
}
