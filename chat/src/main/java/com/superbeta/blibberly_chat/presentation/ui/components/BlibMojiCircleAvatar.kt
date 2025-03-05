package com.superbeta.blibberly_chat.presentation.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.superbeta.blibberly_models.PhotoMetaData

@Composable
fun BlibMojiCircleAvatar(photoMetaData: com.superbeta.blibberly_models.PhotoMetaData) {
    SubcomposeAsyncImage(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape),
        model = photoMetaData.blibmojiUrl,
        contentDescription = "",
        clipToBounds = true
    ) {
        val state = painter.state
        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
            CircularProgressIndicator()
        } else {
            SubcomposeAsyncImageContent()
        }
    }
}