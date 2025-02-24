package info.test

data class Period(
    val name: String,
    val startTime: Long,
    val endTime: Long,
    val volumeMusic: Int,
    val volumeAds: Int,

)

data class Playlist<T : ContentType>(
    val imageUrl: String,
)

enum class ContentType(val string: String) {
    ADS("slip"),
    MUSIC("music"),
    VIDEO("video"),
    PHOTO("photo")
}