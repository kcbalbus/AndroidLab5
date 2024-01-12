package com.example.androidlab3

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.androidlab5.MainMenuScreen
import com.example.androidlab5.PhotoMenuScreen
import com.example.androidlab5.PhotoViewModel
import com.example.androidlab5.R

enum class AppScreen(@StringRes val title: Int) {
    UploadPhoto(title = R.string.upload_image),
    PhotoMenu(title = R.string.analyze_image),
    PhotoText(title = R.string.extract_text),
    PhotoObjects(title = R.string.find_objects)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryAppBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = stringResource(currentScreen.title), color = Color.White) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        tint = Color.White
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoApp(
    photoViewModel: PhotoViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.UploadPhoto.name
    )

    Scaffold(
        topBar = {
            GalleryAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val photoState by photoViewModel.photoState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = AppScreen.UploadPhoto.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreen.UploadPhoto.name) {
                MainMenuScreen(photoViewModel, photoState, {navController.navigate(AppScreen.PhotoMenu.name)})
            }
            composable(route = AppScreen.PhotoMenu.name) {
                PhotoMenuScreen(photoViewModel, photoState)
            }
        }
    }
}