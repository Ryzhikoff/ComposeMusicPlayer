package info.test

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.*
import platform.AVKit.AVPlayerViewController
import platform.CoreGraphics.CGRect
import platform.Foundation.NSURL
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)

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
//    val player = remember { AVPlayer(uRL = NSURL.URLWithString(url)!!) }
//    val playerLayer = remember { AVPlayerLayer() }
//    val avPlayerViewController = remember { AVPlayerViewController() }
//    avPlayerViewController.player = player
//    avPlayerViewController.showsPlaybackControls = true
//
//    playerLayer.player = player
//    // Используйте UIKitView для интеграции с существующими представлениями UIKit
//    UIKitView(
//        factory = {
//            // Создайте UIView для хранения AVPlayerLayer
//            val playerContainer = UIView()
//            playerContainer.addSubview(avPlayerViewController.view)
//            // Возвращаем playerContainer как корневой UIView
//            playerContainer
//        },
//        onRelease = { view: UIView ->
//            CATransaction.begin()
//            CATransaction.setValue(true, kCATransactionDisableActions)
//        },
////        onResize = { view: UIView, rect: CValue<CGRect> ->
////            CATransaction.begin()
////            CATransaction.setValue(true, kCATransactionDisableActions)
////            view.layer.setFrame(rect)
////            playerLayer.setFrame(rect)
////            avPlayerViewController.view.layer.frame = rect
////            CATransaction.commit()
////        },
//        update = { view ->
//            player.play()
//            avPlayerViewController.player!!.play()
//        },
//        modifier = modifier
//    )
//}