package com.mmfsin.grisaceo.domain.models

import com.google.gson.annotations.SerializedName

data class Urls(
    @SerializedName("url_main") val urlMain: String,
    @SerializedName("url_tshirts") val urlTshirts: String,
    @SerializedName("url_complements") val urlComplements: String,
    @SerializedName("url_designs") val urlDesigns: String
)