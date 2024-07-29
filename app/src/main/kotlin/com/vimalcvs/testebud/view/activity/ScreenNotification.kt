package com.vimalcvs.testebud.view.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vimalcvs.testebud.model.ModelNotification
import com.vimalcvs.testebud.util.EmptyView
import com.vimalcvs.testebud.util.ToolbarBack
import com.vimalcvs.testebud.viewmodel.ViewModelRoom


@Composable
fun NotificationScreen(navController: NavController, viewModel: ViewModelRoom) {
    val notification by viewModel.allNotifications.collectAsState(initial = emptyList())
    val isEmpty by viewModel.isEmptyNotification.collectAsState(initial = false)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ToolbarBack(title = "Notification") {
            navController.popBackStack()
        }
        when {
            isEmpty -> EmptyView()
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(notification) { item ->
                        ListItemNotification(item = item)
                    }
                    item {
                        Spacer(modifier = Modifier.height(60.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ListItemNotification(item: ModelNotification) {
    Column(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize(),
    ) {
        Card(
            modifier = Modifier
                .aspectRatio(1f)
                .clickable {


                }
        ) {
            Text(
                text = item.body,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    .fillMaxSize()
            )
        }

    }
}