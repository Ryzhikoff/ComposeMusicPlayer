package info.test

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.media.MediaRef
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.base.MediaPlayerEventListener
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent
import java.awt.Component
import java.util.*
import javax.swing.SwingUtilities

@Composable
actual fun PlayerVideo(
    modifier: Modifier,
    url: String,
    onFinish: (() -> Unit)?
) {
    val mediaPlayerComponent = remember { initializeMediaPlayerComponent() }
    val mediaPlayer = remember { mediaPlayerComponent.mediaPlayer() }
    mediaPlayer.setupVideoFinishHandler(onFinish)

    val factory = remember { { mediaPlayerComponent } }

    // Флаг для проверки готовности компонента
    var isSurfaceReady by remember { mutableStateOf(false) }

    // Проверяем готовность компонента на UI-потоке
    DisposableEffect(Unit) {
        SwingUtilities.invokeLater {
            isSurfaceReady = mediaPlayerComponent.isDisplayable
        }
        onDispose {}
    }

    // Запуск воспроизведения только когда компонент готов
    LaunchedEffect(url, isSurfaceReady) {
        if (isSurfaceReady) {
            println("surface ready")
            mediaPlayer.events().addMediaPlayerEventListener(object : MediaPlayerEventAdapter() {
                override fun mediaChanged(mediaPlayer: MediaPlayer?, media: MediaRef?) {
                    super.mediaChanged(mediaPlayer, media)
                    println("media changed")
                }
            })
            println(url)
            mediaPlayer.media().play(url)
        }
    }

    SwingPanel(
        factory = factory,
        background = Color.Transparent,
        modifier = modifier
    )
}

/**
 * See https://github.com/caprica/vlcj/issues/887#issuecomment-503288294
 * for why we're using CallbackMediaPlayerComponent for macOS.
 */
private fun initializeMediaPlayerComponent(): Component {
    NativeDiscovery().discover()
    return if (isMacOS()) {
        CallbackMediaPlayerComponent()
    } else {
        EmbeddedMediaPlayerComponent()
    }
}

/**
 * We play the video again on finish (so the player is kind of idempotent),
 * unless the [onFinish] callback stops the playback.
 * Using `mediaPlayer.controls().repeat = true` did not work as expected.
 */
@Composable
private fun MediaPlayer.setupVideoFinishHandler(onFinish: (() -> Unit)?) {
    DisposableEffect(onFinish) {
        val listener = object : MediaPlayerEventAdapter() {
            override fun finished(mediaPlayer: MediaPlayer) {
                onFinish?.invoke()
                mediaPlayer.submit { mediaPlayer.controls().play() }
            }
        }
        events().addMediaPlayerEventListener(listener)
        onDispose { events().removeMediaPlayerEventListener(listener) }
    }
}


/**
 * Returns [MediaPlayer] from player components.
 * The method names are the same, but they don't share the same parent/interface.
 * That's why we need this method.
 */
private fun Component.mediaPlayer() = when (this) {
    is CallbackMediaPlayerComponent -> mediaPlayer()
    is EmbeddedMediaPlayerComponent -> mediaPlayer()
    else -> error("mediaPlayer() can only be called on vlcj player components")
}

private fun isMacOS(): Boolean {
    val os = System
        .getProperty("os.name", "generic")
        .lowercase(Locale.ENGLISH)
    return "mac" in os || "darwin" in os
}

//@Composable
//actual fun PlayerVideo(modifier: Modifier, url: String) {
//    val mediaPlayerComponent = remember { initializeMediaPlayerComponent() }
//    val mediaPlayer = remember { mediaPlayerComponent.mediaPlayer() }
//    mediaPlayer.emitProgressTo(progressState)
//    mediaPlayer.setupVideoFinishHandler(onFinish)
//
//    LaunchedEffect(url) { mediaPlayer.media().play /*OR .start*/(url) }
//    DisposableEffect(Unit) { onDispose(mediaPlayer::release) }
//    SwingPanel(
//        factory = factory,
//        background = Color.Transparent,
//        modifier = modifier,
//        update = {
//
//        }
//    )
//
//    Box(modifier = Modifier.size(300.dp).background(Color.Blue)) {  }
//}
//
//private fun initializeMediaPlayerComponent(): Component {
//    NativeDiscovery().discover()
//    return if (isMacOS()) {
//        CallbackMediaPlayerComponent()
//    } else {
//        EmbeddedMediaPlayerComponent()
//    }
//}
//
//
//private fun Component.mediaPlayer() = when (this) {
//    is CallbackMediaPlayerComponent -> mediaPlayer()
//    is EmbeddedMediaPlayerComponent -> mediaPlayer()
//    else -> error("mediaPlayer() может быть вызван только для компонентов проигрывателя vlcj")
//}
//
//private fun isMacOS(): Boolean {
//    val os = System
//        .getProperty("os.name", "generic")
//        .lowercase(Locale.ENGLISH)
//    return "mac" in os || "darwin" in os
//}
