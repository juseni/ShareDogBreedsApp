package com.share.data.source.response

import com.google.gson.annotations.SerializedName

/**
 * @author Juan Sebastian Niño
 */
data class ResponseMessageData(
    @SerializedName("message") val dogBreedsResponse: Map<String, List<String>> = emptyMap(),
    @SerializedName("status") val status: String? = null
)

data class ResponseDogImagesData(
    @SerializedName("message") val dogImages: List<String>? = emptyList(),
    @SerializedName("status") val status: String? = null
)
