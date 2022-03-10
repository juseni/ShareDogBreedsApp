package com.share.data.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @author Juan Sebastian Niño
 */
inline fun <reified T> Gson.mapToList(list: List<Any?>): T =
    fromJson(toJson(list), object : TypeToken<T>() {}.type)

inline fun <reified T> Gson.mapToObject(any: Any?): T =
    fromJson(toJson(any), object : TypeToken<T>() {}.type)

fun Int.isUpdateSuccessful() = this > 0