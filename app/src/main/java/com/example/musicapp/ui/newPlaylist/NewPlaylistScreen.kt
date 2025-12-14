package com.example.musicapp.ui.newPlaylist

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.musicapp.R
import com.example.musicapp.ui.components.common.DisplayError
import org.koin.compose.koinInject


@Composable
fun NewPlaylistScreen(navController: NavController) {
    val vm: NewPlaylistViewModel = koinInject()
    val state by vm.newPlaylistState.collectAsState()
    val coverImageUri by vm.coverImageUri.collectAsState()
    NewPlaylistContent(
        createPlaylist = vm::createNewPlayList,
        navController,
        state,
        LocalContext.current,
        coverImageUri,
        vm::setCoverImageUri
    )
}

@Composable
private fun NewPlaylistContent(
    createPlaylist: (playlistName: String, playlistDescription: String) -> Unit,
    navController: NavController,
    newPlaylistState: NewPlaylistState,
    context: Context,
    coverImage: String?,
    setImageURI: (String) -> Unit,
) {

    when (val state = newPlaylistState) {
        is NewPlaylistState.Error -> DisplayError.displayToastError(context, state.error)
        NewPlaylistState.Initial -> {
            Form(createPlaylist, coverImage, setImageURI)
        }

        NewPlaylistState.Loading,
        NewPlaylistState.Success -> {
            navController.popBackStack()
            Toast.makeText(context, stringResource(R.string.playlist_created), Toast.LENGTH_LONG)
                .show()
        }

    }
}

@Composable
private fun Form(
    onClick: (String, String) -> Unit,
    coverImageUri: String?,
    setImageURI: (String) -> Unit
) {
    var playlistName by remember { mutableStateOf("") }
    var playlistDescription by remember { mutableStateOf("") }
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        // Обработка результата выбора изображения
        uri?.let {
            setImageURI(it.toString())
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            imagePickerLauncher.launch("image/*")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clickable {
                    // Для Android 13+ (API 33+) разрешения не нужны для выбора изображений
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        imagePickerLauncher.launch("image/*")
                    } else {
                        when {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED -> {
                                imagePickerLauncher.launch("image/*")
                            }

                            else -> {
                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    }
                }
        ) {
            if(coverImageUri === null){
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.library_light),
                contentDescription = stringResource(R.string.playlist_name),
                colorFilter = ColorFilter.tint(Color.Gray)
            )
            } else {
                AsyncImage(
                    model = coverImageUri.toUri(),
                    contentDescription = stringResource(R.string.playlist_name),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Box(modifier = Modifier.padding(top=16.dp)) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                placeholder = {
                    Text(
                        stringResource(R.string.playlist_name),
                        modifier = Modifier.alpha(0.7f)
                    )
                },
                shape = RoundedCornerShape(8.dp),
                value = playlistName,
                onValueChange = { playlistName = it }
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            //singleLine = true,
            placeholder = {
                Text(
                    stringResource(R.string.playlist_description),
                    modifier = Modifier.alpha(0.7f)
                )
            },
            shape = RoundedCornerShape(8.dp),
            value = playlistDescription,
            onValueChange = { playlistDescription = it }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                if (playlistName.isNotEmpty()) {
                    onClick(playlistName, playlistDescription)
                }
            }

        ) {
            Text(stringResource(R.string.create))
        }
    }
}


