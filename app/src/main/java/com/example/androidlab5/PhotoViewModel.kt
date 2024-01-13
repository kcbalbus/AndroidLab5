package com.example.androidlab5

import android.app.Application
import android.graphics.RectF
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.IOException

class PhotoViewModel(application: Application) : ViewModel() {
    private val _photoState = MutableStateFlow(PhotoState())
    val photoState: StateFlow<PhotoState> = _photoState.asStateFlow()
    private val appContext = application.applicationContext


    fun updatePhoto (uri: Uri) {
        _photoState.update { currentState ->
            currentState.copy(
                uri = uri
            )
        }
    }

    fun updateRecognizedText (text: String) {

        _photoState.update { currentState ->
            currentState.copy(
                recognizedText = text
            )
        }
    }

    fun updateRecognizedObjects (list: List<ImageLabel>) {

        _photoState.update { currentState ->
            currentState.copy(
                recognizedObjects = list
            )
        }
    }

    fun textRecognition() {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        val image: InputImage
        try {
            image = InputImage.fromFilePath(appContext, photoState.value.uri)

            val result = recognizer.process(image)
                .addOnSuccessListener {text ->
                    processTextRecognition(text)
                }
                .addOnFailureListener { e ->
                    Log.e("TextRecognition", e.toString())
                    updateRecognizedText("Wystąpił błąd, spróbuj ponownie!")
                }

        } catch (e: IOException) {
            Log.e("TextRecognition", e.toString())
            updateRecognizedText("Wystąpił błąd, spróbuj ponownie!")
        }
    }

    fun processTextRecognition(text: Text) {

        val blocks: List<Text.TextBlock> = text.textBlocks
        if (blocks.isEmpty()) {
            updateRecognizedText("Brak tekstu")
        } else {
            val sortedBlocks = blocks.sortedBy { it.boundingBox?.top }
            val sortedText = sortedBlocks.joinToString("\n") { it.text }
            updateRecognizedText(sortedText)
        }


    }


    fun objectsRecognition() {
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

        val image: InputImage
        try {
            image = InputImage.fromFilePath(appContext, photoState.value.uri)

            val result = labeler.process(image)
                .addOnSuccessListener {labels ->
                    processObjectRecognition(labels)
                }
                .addOnFailureListener { e ->
                    Log.e("ObjectRecognition", e.toString())
                    updateRecognizedObjects(emptyList())
                }

        } catch (e: IOException) {
            Log.e("ObjectRecognition", e.toString())
            updateRecognizedObjects(emptyList())
        }
    }

    fun processObjectRecognition(labels: List<ImageLabel>) {

        updateRecognizedObjects(labels)

    }


}