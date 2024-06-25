package com.vimalcvs.testebud.view.activity


import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.widget.StateSet.TAG
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vimalcvs.testebud.R
import com.vimalcvs.testebud.database.Repository
import com.vimalcvs.testebud.view.fragment.FragmentCategory
import com.vimalcvs.testebud.view.fragment.FragmentFavorite
import com.vimalcvs.testebud.view.fragment.FragmentHome
import com.vimalcvs.testebud.notification.FCMNotificationService
import com.vimalcvs.testebud.theme.TesteBudTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TesteBudTheme {
                MainScreen()
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_LOW,
                ),
            )
        }
        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras?.getString(key)
                Log.d(TAG, "Key: $key Value: $value")
            }
        }
        askNotificationPermission()
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationPermission = Manifest.permission.POST_NOTIFICATIONS
            val isPermissionDenied =
                checkSelfPermission(notificationPermission) != PackageManager.PERMISSION_GRANTED
            val isNeverDeniedOrCurrentlyGranted =
                !shouldShowRequestPermissionRationale(notificationPermission)
            if (isPermissionDenied && isNeverDeniedOrCurrentlyGranted) {
                requestPermissions(arrayOf(notificationPermission), 0)
            }
        }
    }
}


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { Toolbar("TesteBud") },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationGraph(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(title: String) {

    val context = LocalContext.current
    val repository: Repository = Repository.getInstance(context)!!

    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        actions = {
            IconButton(onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        repository.deleteAllNotification()
                    } catch (e: Exception) {
                        Log.e(
                            FCMNotificationService.TAG,
                            "Error inserting notification: ${e.message}"
                        )
                    }
                }
            }) {
                Icon(Icons.Outlined.Search, contentDescription = "More")
            }
            IconButton(onClick = {
                context.startActivity(Intent(context, ActivityNotification::class.java))
            }) {
                Icon(Icons.Outlined.Notifications, contentDescription = "More")
            }
            IconButton(onClick = {
                context.startActivity(Intent(context, ActivityNotification::class.java))
            }) {
                Icon(Icons.Outlined.MoreVert, contentDescription = "More")
            }
        }
    )


}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry.value?.destination?.route
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Rounded.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentDestination == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Rounded.Category, contentDescription = "Category") },
            label = { Text("Category") },
            selected = currentDestination == "category",
            onClick = {
                navController.navigate("category") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Rounded.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") },
            selected = currentDestination == "favorite",
            onClick = {
                navController.navigate("favorite") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    TesteBudTheme {
        MainScreen()
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") { FragmentHome() }
        composable("category") { FragmentCategory() }
        composable("favorite") { FragmentFavorite() }
    }
}

