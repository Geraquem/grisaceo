package com.mmfsin.grisaceo.domain.models

import com.google.gson.annotations.SerializedName

data class Gist(
    @SerializedName("url") val url: String,
    @SerializedName("id") val id: String,
    @SerializedName("files") val files: Files
)

data class Files(
    @SerializedName("grisaceo_urls.json") val urls: GrisaceoUrls
)

data class GrisaceoUrls(
    @SerializedName("content") val content: String
)