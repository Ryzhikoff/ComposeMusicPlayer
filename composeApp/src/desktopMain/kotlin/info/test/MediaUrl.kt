package info.test

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class MediaUrl actual constructor(){
    actual fun getVideoUrl(): String {
        return "C:\\Users\\Stepan\\AndroidStudioProjects\\ComposeMusicPlayer\\composeApp\\src\\commonMain\\resources\\video.mp4"
    }
    actual fun getAudioUrl(): String {
        return "C:\\Users\\Stepan\\AndroidStudioProjects\\ComposeMusicPlayer\\composeApp\\src\\commonMain\\resources\\music.mp3"
    }
}