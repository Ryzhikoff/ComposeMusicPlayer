package info.test

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.sun.jna.NativeLibrary
import org.jetbrains.compose.resources.Resource
import uk.co.caprica.vlcj.binding.lib.LibVlc
import uk.co.caprica.vlcj.binding.support.runtime.RuntimeUtil
import java.io.File

fun main() = application {

//    prepareVLC()

    val resPath = System.getProperty("compose.application.resources.dir")
    println(resPath)
    NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), resPath)


    if (!LibVlc.libvlc_get_version().isNullOrEmpty()) {
        println("VLC loaded")
    } else {
        println("not loaded")
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Music Player",
    ) {
        App()
    }
}

private fun prepareVLC() {
    val vlcPath = when {
        System.getProperty("os.name").contains("Windows") -> {
            when (val arch = System.getProperty("os.arch")) {
                "amd64" -> System.getProperty("user.dir") + "\\vlc\\windows\\x64"
                "x86" -> System.getProperty("user.dir") + "\\vlc\\windows\\x86"
                else -> throw IllegalStateException("Unsupported Windows architecture: $arch")
            }
        }
        System.getProperty("os.name").contains("Linux") -> {
            System.getProperty("user.dir") + "/vlc/linux/x64"
        }
        System.getProperty("os.name").contains("Mac") -> {
            System.getProperty("user.dir") + "/vlc/macos/x64"
        }
        else -> throw IllegalStateException("Unsupported platform")
    }

    File(vlcPath).takeIf { it.exists() } ?: throw IllegalStateException("VLC libraries not found at $vlcPath")

    NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), vlcPath)
}