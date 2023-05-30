package com.daabsoft.pokemon.core

sealed class Resource<T : Any> {
    class Loading<T : Any>(val title: String?, val message: String) : Resource<T>()
    class Success<T : Any>(val data: T) : Resource<T>()
    class Error<T : Any>(val code: Int) : Resource<T>()
}