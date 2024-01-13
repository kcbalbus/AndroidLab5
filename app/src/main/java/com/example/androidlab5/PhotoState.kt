package com.example.androidlab5

import android.net.Uri
import com.google.mlkit.vision.label.ImageLabel

data class PhotoState (
        val uri: Uri = Uri.EMPTY,
        val recognizedText: String = "",
        val recognizedObjects: List<ImageLabel> = emptyList()
)