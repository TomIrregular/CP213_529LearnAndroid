package com.tomweasley.overgrilled.data

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest

@Composable
fun GifImage(
    resourceId: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit // Added default parameter
) {
    val context = LocalContext.current

    val imageRequest = ImageRequest.Builder(context)
        .data(resourceId)
        .decoderFactory(if (Build.VERSION.SDK_INT >= 28) {
            ImageDecoderDecoder.Factory()
        } else {
            GifDecoder.Factory()
        })
        .build()

    AsyncImage(
        model = imageRequest,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale // Apply the scale here
    )
}