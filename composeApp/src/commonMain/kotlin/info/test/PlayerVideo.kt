package info.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

data class Progress(
    val fraction: Float,
    // TODO: Use kotlin.time.Duration when Kotlin version is updated.
    //  See https://github.com/Kotlin/api-guidelines/issues/6
    val timeMillis: Long
)

@Composable
expect fun PlayerVideo(
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
)