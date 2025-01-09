package info.test

import android.widget.VideoView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun PlayerVideo(modifier: Modifier, url: String) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            VideoView(context).apply {
                setVideoPath(url)
                val mediaController = android.widget.MediaController(context)
                mediaController.setAnchorView(this)
                setMediaController(mediaController)
                start()
            }
        },
        update = {})
}