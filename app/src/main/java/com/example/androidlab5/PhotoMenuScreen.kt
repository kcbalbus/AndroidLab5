package com.example.androidlab5

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter

@Composable
fun PhotoMenuScreen(photoViewModel: PhotoViewModel, photoState: PhotoState, navigateToTextRec:()->Unit, naviagteToObjectsRec:()->Unit) {

    Column (
        modifier = Modifier.fillMaxSize()
            ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .background(color = Color.Black)
        ) {
            Image(
                painter = rememberImagePainter(photoState.uri),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Button(
            shape = RectangleShape,
            onClick = {
                photoViewModel.textRecognition()
                navigateToTextRec()
            },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.49f)
        ) {
            Text(
                text = "Rozpoznaj tekst",
                style = MaterialTheme.typography.titleLarge
            )

        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.Gray)
        )
        Button(
            shape = RectangleShape,
            onClick = {
                photoViewModel.objectsRecognition()
                naviagteToObjectsRec()
            },
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "Rozpoznaj obiekty",
                style = MaterialTheme.typography.titleLarge
            )

        }
    }
    

    
}