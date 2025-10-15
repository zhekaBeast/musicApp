package com.example.musicapp.playingAround

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayAround {
}


@Composable
fun LazyTest(){

    val listState = rememberLazyListState() // Запоминаем состояние списка
    val coroutineScope = rememberCoroutineScope() // Получаем скоуп корутины
    var computers by remember { mutableStateOf(
        computers
    )}
    Box (modifier = Modifier.fillMaxSize()) {
        ComputerList(computers = computers, listState = listState)
        Column(
            modifier = Modifier.fillMaxSize().padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { computers = computersNew }
            ) {
                Text("Получить обновление списка")
            }
            Button(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            ) {
                Text("В начало списка")
            }
        }
    }
}

@Composable
fun ComputerList(computers: List<Computer>, listState: LazyListState) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = computers.size,
            key = { index -> computers[index].id }
        ) { index ->
            ComputerItem(computers[index])
        }
    }
}

@Composable
fun ComputerItem(computer: Computer) { // Функция отображения данных одного элемента списка
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Model: ${computer.model}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Processor: ${computer.processor}")
            Text(text = "RAM: ${computer.ramSizeGB} GB")
        }
    }
}
data class Computer(
    val id: Int,
    val model: String,
    val processor: String,
    val ramSizeGB: Int
)
val computers = listOf(
    Computer(1,"IBM","I7",16),
    Computer(2,"IBM","I5",8),
    Computer(3,"IBM","I7",32),
    Computer(4,"HP","C5",16),
    Computer(5,"Lenovo","I7",32),
    Computer(6,"HP","C3",8),
    Computer(7,"EDELWEISS","I5",64),
    Computer(8,"EDELWEISS","I7",64),
    Computer(9,"HP","C7",32),
)
val computersNew = listOf(
    Computer(1,"IBM","I7",16),
    Computer(10,"New","I7",16),
    Computer(11,"MC","I5",8),
    Computer(12,"MC","I7",32),
    Computer(13,"IBM","I7",32),
    Computer(2,"IBM","I5",8),
    Computer(3,"IBM","I7",32),
    Computer(4,"HP","C5",16),
    Computer(5,"Lenovo","I7",32),
    Computer(6,"HP","C3",8),
    Computer(7,"EDELWEISS","I5",64),
    Computer(8,"EDELWEISS","I7",64),
    Computer(9,"HP","C7",32),
)

@Composable
fun MovieTest(){
    val gson = Gson()
    val movies = listOf(
        Movie("1","Первый фильм"),
        Movie("2","Второй фильм"),
        Movie("3","Третий фильм"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Test() {
    //val VM = TextViewModel()
    //TextFieldWithViewModel(VM)
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var isClicked by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isClicked) Color.Red else Color.Blue

    )

    Column(Modifier.padding(top = 24.dp).fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
        var password by remember { mutableStateOf("") }
        var passwordVisibility by remember { mutableStateOf(false) }
        var error by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = password,
            label = { Text("password") },
            onValueChange = {
                password = it
                error = it.length > 10
            },
            isError = error,
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Visibility Icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFF3F51B5),
                unfocusedTextColor = Color(0xff888888),
                focusedContainerColor = Color.White,
                focusedTextColor = Color(0xff222222),
                errorContainerColor = Color(0xFFF44336)
            )
        )

        TimedColorChangeAnimation()
        AlphaAnimationExample()
        VisibilityAnimationExample()
        PositionAnimationExample()
        SizeAnimationExample()
    }
}

@Composable
fun AlphaAnimationExample() {
    var isClicked by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isClicked) 0f else 1f,
    )

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Red.copy(alpha = alpha))
            .clickable { isClicked = !isClicked }
    )
}

@Composable
fun VisibilityAnimationExample() {
    var isVisible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Yellow)
                .clickable { isVisible = false }
        )
    }
}

@Composable
fun PositionAnimationExample() {
    var isClicked by remember { mutableStateOf(false) }
    val offset by animateIntOffsetAsState(
        targetValue = if (isClicked) IntOffset(100, 100) else IntOffset(0, 0), label = ""
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .offset { offset }
                .size(100.dp)
                .background(Color.Yellow)
                .clickable { isClicked = !isClicked }
        )
    }
}

@Composable
fun SizeAnimationExample() {
    var isClicked by remember { mutableStateOf(false) }
    val size by animateDpAsState(
        targetValue = if (isClicked) 200.dp else 100.dp
    )

    Box(
        modifier = Modifier
            .size(size)
            .background(Color.Green)
            .clickable { isClicked = !isClicked }
    )
}

@Composable
fun TimedColorChangeAnimation() {
    // Список цветов для анимации
    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow)
    // Индекс текущего цвета
    var currentColorIndex by remember { mutableStateOf(0) }

    val backgroundColor = remember { Animatable(colors[currentColorIndex]) }

    // Используем LaunchedEffect для запуска анимации по таймеру
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000) // Ждём 2 секунды
            currentColorIndex = (currentColorIndex + 1) % colors.size
            backgroundColor.animateTo(
                targetValue = colors[currentColorIndex],
                animationSpec = tween(durationMillis = 1000)
            )
        }
    }

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(backgroundColor.value)
    )
}

@Composable
fun TextFieldWithViewModel(viewModel: TextViewModel) {
    val text = viewModel.text.collectAsState().value
    val title = viewModel.title.collectAsState().value
    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        topBar = { TopAppBar(title = { Text(title) }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.updateTitle(text) }) {
                Icon(Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) {
        TextField(
            modifier = Modifier.padding(it),
            value = text,
            onValueChange = { newText ->
                viewModel.onChange(newText)
            },
            label = { Text("Введите текст") }
        )
    }
}

class TextViewModel() : ViewModel() {
    private val _text = MutableStateFlow("")
    val text: StateFlow<String> = _text.asStateFlow()

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    fun onChange(newText: String) {
        _text.value = newText
    }

    fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }
}