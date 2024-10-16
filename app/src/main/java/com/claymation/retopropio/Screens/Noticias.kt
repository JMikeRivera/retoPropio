package com.claymation.retopropio.Screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.claymation.retopropio.Viewmodels.ViewModel

@Composable
fun NoticiasScreen(navController: NavController?) {
    val viewModel: ViewModel = viewModel()
    val noticias by viewModel.noticias.collectAsState()
    val context = LocalContext.current

    // Patrón de 2 pequeños a la izquierda, 1 grande a la derecha, etc.
    val gridItems = mutableListOf<ViewModel.Noticia>()
    gridItems.addAll(noticias)

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(gridItems) { index, noticia ->
            when {
                index % 3 == 0 -> { // Primer elemento grande
                    NoticiaCardGrande(noticia = noticia, onClick = {
                        navController?.navigate("NoticiaDetalle/${noticia.Noticia_id}")
                    })
                }
                else -> { // Elementos pequeños
                    NoticiaCardPequena(noticia = noticia, onClick = {
                        navController?.navigate("NoticiaDetalle/${noticia.Noticia_id}")
                    })
                }
            }
        }
    }
}

@Composable
fun NoticiaCardGrande(noticia: ViewModel.Noticia, onClick: () -> Unit) {
    val modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1.5f)
        .clickable { onClick() }

    if (noticia.IsVideo) {
        VideoPlayer(videoUrl = noticia.Link, modifier = modifier)
    } else {
        ImageCard(noticia = noticia, modifier = modifier)
    }
}

@Composable
fun NoticiaCardPequena(noticia: ViewModel.Noticia, onClick: () -> Unit) {
    val modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f)
        .clickable { onClick() }

    if (noticia.IsVideo) {
        // En caso de que se requiera, manejar videos en cuadros pequeños
        VideoPlayer(videoUrl = noticia.Link, modifier = modifier)
    } else {
        ImageCard(noticia = noticia, modifier = modifier)
    }
}

@Composable
fun ImageCard(noticia: ViewModel.Noticia, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = rememberAsyncImagePainter(noticia.Link),
            contentDescription = noticia.Nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f))
                .padding(4.dp)
        ) {
            Text(
                text = noticia.Nombre,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun VideoPlayer(videoUrl: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(
        AndroidView(
            modifier = modifier,
            factory = { ctx ->
                // Usamos PlayerView de androidx.media3 en lugar de com.google.android.exoplayer2
                androidx.media3.ui.PlayerView(ctx).apply {
                    player = exoPlayer // Aquí ya no es necesario el cast
                    useController = false
                }
            }
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}
