package com.example.androidlab5

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter

@Composable
fun TextRecognitionScreen(photoViewModel: PhotoViewModel, photoState: PhotoState) {
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = rememberImagePainter(photoState.uri),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
        )
        Text(text = photoState.recognizedText)
    }
}