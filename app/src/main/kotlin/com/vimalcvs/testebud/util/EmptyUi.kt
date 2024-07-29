package com.vimalcvs.testebud.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vimalcvs.testebud.R
import com.vimalcvs.testebud.viewmodel.ViewModelMain
import com.vimalcvs.testebud.viewmodel.ViewModelRoom

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarBack(title: String, onBackClick: () -> Unit) {
    val displayTitle = title.split(" ").take(2).joinToString(" ")
    TopAppBar(
        title = {
            Text(
                text = displayTitle,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarSearch(
    query: String,
    onQueryChange: (String) -> Unit,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            OutLineTextFieldAutoSelected(query = query, onQueryChange = onQueryChange)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
    )
}

@Composable
fun OutLineTextFieldAutoSelected(
    query: String,
    onQueryChange: (String) -> Unit,
) {
    val searchTextFieldValue = remember { mutableStateOf(TextFieldValue(query)) }
    onQueryChange(searchTextFieldValue.value.text)

    Column(
        modifier = Modifier
            .padding(end = 16.dp)
    ) {
        TextField(
            value = searchTextFieldValue.value,
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .onFocusChanged { state ->
                    if (state.hasFocus) {
                        searchTextFieldValue.value = searchTextFieldValue.value.copy(
                            selection = TextRange(
                                0,
                                searchTextFieldValue.value.text.length
                            )
                        )
                    }
                },
            onValueChange = {
                searchTextFieldValue.value = it
            },
            shape = RoundedCornerShape(50.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "Search here...",
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                autoCorrect = true,
            ),
        )
    }

}


@Composable
fun NoNetworkView(viewModel: ViewModelMain) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_no_network),
            contentDescription = "No Network",
            modifier = Modifier.size(110.dp)
        )
        Text(
            text = "No Network Connection",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            viewModel.fetchLamb()
            viewModel.fetchBreakfast()
            viewModel.fetchPasta()
            viewModel.fetchPork()
            viewModel.fetchSide()
            viewModel.fetchVegetarian()
            viewModel.fetchSlider()
        }) {
            Text(text = "Retry")
        }
    }
}


@Composable
fun NoNetworkView(viewModel: ViewModelRoom) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_no_network),
            contentDescription = "No Network",
            modifier = Modifier.size(110.dp)
        )
        Text(
            text = "No Network Connection",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            viewModel.fetchCategories()
        }) {
            Text(text = "Retry")
        }
    }
}


@Composable
fun EmptyView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_empty_box),
            contentDescription = "No Data",
            modifier = Modifier.size(110.dp)
        )
        Text(
            text = "No Data Available",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(

            strokeCap = StrokeCap.Round,
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "Loading...",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
        )
    }
}
