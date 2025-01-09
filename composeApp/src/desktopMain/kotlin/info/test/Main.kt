package info.test

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.sun.jna.NativeLibrary

fun main() = application {

    NativeLibrary.addSearchPath("libvlc", "build/libs/vlc")

    Window(
        onCloseRequest = ::exitApplication,
        title = "Music Player",
    ) {
        App()
    }
}