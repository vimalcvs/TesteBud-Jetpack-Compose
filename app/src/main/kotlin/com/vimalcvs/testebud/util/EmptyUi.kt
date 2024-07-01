package com.vimalcvs.testebud.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vimalcvs.testebud.R
import com.vimalcvs.testebud.viewmodel.ViewModelMain
import com.vimalcvs.testebud.viewmodel.ViewModelRoom


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
