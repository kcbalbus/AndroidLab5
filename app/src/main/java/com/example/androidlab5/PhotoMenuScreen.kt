package com.example.androidlab5

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter

@Composable
fun PhotoMenuScreen(photoViewModel: PhotoViewModel, photoState: PhotoState) {
    
    Image(painter = rememberImagePainter(photoState.uri), contentDescription = null, Modifier.fillMaxSize())
    
}