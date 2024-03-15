package uk.co.ivano.gameapp.core.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class GameMode {
    @SerialName("fruits")
    Fruits,
    @SerialName("vegetables")
    Vegetables,
    @SerialName("flowers")
    Flowers
}