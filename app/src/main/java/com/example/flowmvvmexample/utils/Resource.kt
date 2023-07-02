package com.example.flowmvvmexample.utils

sealed class Resource<T>(
    val data: T? = null,
    val errorCode: Int? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>() : Resource<T>(null )
    class DataError<T>(errorCode: Int) : Resource<T>(null, errorCode)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is DataError -> "Error[exception=$errorCode]"
            is Loading<T> -> "Loading"
        }
    }
}