package dev.isohn.models
import kotlinx.serialization.Serializable


@Serializable
data class Talents(
    val id: String,
    val name: String,
    /*val genre: String,
    val description: String,
    val thumbnail: String,
    val socials: List<String>,
    val videos: List<String>*/
)

val talentsStorage = mutableListOf<Talents>()