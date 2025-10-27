package com.example.musicapp.ui.track

//@Composable
//fun TrackScreen(viewModel: TrackViewModel) {
//    val screenState by viewModel.trackScreenState.collectAsState()
//
//    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
//        when (val state = screenState) {
//            is TrackScreenState.Content -> {
//                TrackScreenContent(viewModel, state) //1
//            }
//
//            is TrackScreenState.Loading -> {
//                CircularProgressIndicator()
//            }
//        }
//    }
//}
//
//
//@Composable
//@OptIn(ExperimentalMaterial3Api::class)
//fun TrackScreenContent(viewModel: TrackViewModel, screenState: TrackScreenState.Content) {
//    val playerStatus by viewModel.playerStatusState.collectAsState() //2
//    when (val state = playerStatus){
//        is PlayerStatusState.Error -> TODO()
//        PlayerStatusState.Initial -> TODO()
//        PlayerStatusState.Loading -> TODO()
//        is PlayerStatusState.PlaybackState -> {
//            val sliderState = rememberSliderState(state.progress)
//            Column {
//                AsyncImage(
//                    model = screenState.trackModel.pictureUrl,
//                    contentDescription = null
//                )
//                Text(screenState.trackModel.author)
//                Text(screenState.trackModel.name)
//
//                val buttonIcon = if (state.isPlaying)  Icons.Default.Stop else Icons.Default.PlayArrow
//                Button(content = { Icon(
//                    buttonIcon,
//                    contentDescription = ""
//                ) }, onClick = { viewModel.play() })   // 4
//
//                Slider(
//                    state = sliderState
//                )
//            }
//        }
//    }
//
//}