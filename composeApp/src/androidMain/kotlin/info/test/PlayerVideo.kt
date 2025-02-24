package info.test

import android.widget.VideoView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun PlayerVideo(
    modifier: Modifier,
    url: String,
//    isResumed: Boolean,
//    volume: Float,
//    speed: Float,
//    seek: Float,
//    isFullscreen: Boolean,
//    progressState: MutableState<Progress>,
//    modifier: Modifier,
    onFinish: (() -> Unit)?
) {

}
//
//@Composable
//actual fun PlayerVideo(modifier: Modifier, url: String) {
//    AndroidView(
//        modifier = modifier,
//        factory = { context ->
//            VideoView(context).apply {
//                setVideoPath(url)
//                val mediaController = android.widget.MediaController(context)
//                mediaController.setAnchorView(this)
//                setMediaController(mediaController)
//                start()
//            }
//        },
//        update = {})
//}