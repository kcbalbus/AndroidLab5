package com.example.androidlab5

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
import androidx.compose.ui.unit.dp

@Composable
fun MainMenuScreen(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        ) {
            Button(
                shape = RectangleShape,
                onClick = { /*TODO*/ },
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
                onClick = { /*TODO*/ },
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

