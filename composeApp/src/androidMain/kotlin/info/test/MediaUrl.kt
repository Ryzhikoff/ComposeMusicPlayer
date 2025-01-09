package info.test

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class MediaUrl actual constructor() {
    actual fun getVideoUrl(): String {
        return "file:///android_asset/video.mp4"
    }
    actual fun getAudioUrl(): String {
        return "file:///android_asset/audio.mp3"
    }
}