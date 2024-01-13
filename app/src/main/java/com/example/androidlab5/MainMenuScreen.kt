package com.example.androidlab5

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun MainMenuScreen(photoViewModel: PhotoViewModel, photoState: PhotoState, onPhotoUploadNavigate:()->Unit){

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->

            if (uri != null) {
                photoViewModel.updatePhoto(uri)
                onPhotoUploadNavigate()
            }
        }
    )

    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".provider", file
    )

    val cameraMedia = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { succes ->
        if (succes) {
            photoViewModel.updatePhoto(uri)
            onPhotoUploadNavigate()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            cameraMedia.launch(uri)
        }
        else {
            Toast.makeText(context, "Nie przyznano dostępu", Toast.LENGTH_SHORT).show()

        }
    }



    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.Gray)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        ) {
            Button(
                shape = RectangleShape,
                onClick = {
                    pickMedia.launch("image/*")
                },
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Wybierz zdjęcie z galerii",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.Gray)
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Button(
                shape = RectangleShape,
                onClick = {
                    val permissionCheckResult = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)

                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraMedia.launch(uri)
                    } else {
                        permissionLauncher.launch(android.Manifest.permission.CAMERA)
                    }
                },
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Zrób zdjęcie",
                    style = MaterialTheme.typography.titleLarge
                )

            }
        }
    }
}

fun Context.createImageFile(): File {
    val timestamp = SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Date())
    val imageFileName = "JPEG_" + timestamp + "_"
    val image = File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )

    return image
}
