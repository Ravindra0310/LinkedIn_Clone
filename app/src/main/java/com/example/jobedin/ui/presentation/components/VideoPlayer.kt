package com.example.jobedin.ui.presentation.components

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

@Composable
fun VideoPlayer(
    url: String
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    var autoPlay by remember { mutableStateOf(true) }
    var window by remember { mutableStateOf(0) }
    var position by remember { mutableStateOf(0L) }



    val player = remember {
        val player = SimpleExoPlayer.Builder(context)
            .build()

        val defaultHttpDataSourceFactory = DefaultHttpDataSourceFactory("test")
        player.prepare(
            HlsMediaSource.Factory(defaultHttpDataSourceFactory)
                .createMediaSource(Uri.parse(url))
        )
        player.playWhenReady = autoPlay
        player.seekTo(window, position)
        player
    }



    fun updateState() {
        autoPlay = player.playWhenReady
        window = player.currentWindowIndex
        position = 0L.coerceAtLeast(player.contentPosition)
    }

    val playerView: PlayerView = remember {
        val playerView = PlayerView(context)
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                playerView.onResume()
                player.playWhenReady = autoPlay
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop() {
                updateState()
                playerView.onPause()
                player.playWhenReady = false
            }
        })
        playerView
    }


    AndroidView(
        factory = { playerView },
        modifier = Modifier
            .fillMaxWidth()
    ) { _ ->
        playerView.player = player
    }
}