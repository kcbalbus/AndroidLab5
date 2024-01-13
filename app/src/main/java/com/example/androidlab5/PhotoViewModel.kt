package com.example.androidlab5

import android.app.Application
import android.graphics.RectF
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
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

    fun textRecognition() {
        val recognizer = TextRecognition.getClient()

        val image: InputImage
        try {
            image = InputImage.fromFilePath(appContext, photoState.value.uri)

            val result = recognizer.process(image)
                .addOnSuccessListener {text ->
                    updateRecognizedText(text.text)
                }
                .addOnFailureListener { e ->
                    Log.e("TextRecognition", e.toString())
                }

        } catch (e: IOException) {
            Log.e("TextRecognition", e.toString())
            updateRecognizedText("Wystąpił błąd, spróbuj ponownie!")
        }
    }

    fun processTextRecognition(text: Text) {
        {
            val blocks: List<Text.TextBlock> = text.getTextBlocks()
            if (blocks.size == 0) {
                updateRecognizedText("Brak tekstu")
            } else {
                updateRecognizedText(text.text)
/*
                for (block in blocks) {
                    val lines = block.lines

                    for (line in lines) {
                        val lineBoundingBox = RectF(line.boundingBox)
                    }
                }*/
            }

        }
    }


    fun ObjectsRecognition() {
        //TODO
    }


}