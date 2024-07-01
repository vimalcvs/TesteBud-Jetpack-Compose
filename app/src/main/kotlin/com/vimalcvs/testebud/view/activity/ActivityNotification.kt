package com.vimalcvs.testebud.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vimalcvs.testebud.view.fragment.ListItemCategory
import com.vimalcvs.testebud.model.ModelCategory
import com.vimalcvs.testebud.model.ModelNotification
import com.vimalcvs.testebud.theme.TesteBudTheme
import com.vimalcvs.testebud.util.EmptyView
import com.vimalcvs.testebud.viewmodel.ViewModelRoom

class ActivityNotification : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TesteBudTheme {
                FragmentNotificationScreen(onBackClick = { finish() })
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(bottom = systemBars.bottom)
            insets
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FragmentNotificationScreen(onBackClick: () -> Unit) {

    val viewModel: ViewModelRoom = viewModel()

    val notification by viewModel.allNotifications.collectAsState(initial = emptyList())
    val isEmpty by viewModel.isEmptyNotification.collectAsState(initial = false)

    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Notification",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            navigationIcon = {
                IconButton(onClick = { onBackClick() }) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                }
            },
        )
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
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize(),
    ) {
        Card(
            modifier = Modifier
                .aspectRatio(1f)
                .clickable {
                    val intent = Intent(context, ActivityDetail::class.java).apply {
                        putExtra("itemName", item.title)
                    }
                    context.startActivity(intent)
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

@Preview(showBackground = true)
@Composable
fun FragmentNotificationPreview() {
    TesteBudTheme {
        FragmentNotificationScreen(onBackClick = {})
    }
}
