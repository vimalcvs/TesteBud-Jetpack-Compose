package com.vimalcvs.testebud.view.activity


import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
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
import androidx.constraintlayout.widget.StateSet.TAG
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vimalcvs.testebud.R
import com.vimalcvs.testebud.database.Repository
import com.vimalcvs.testebud.theme.TesteBudTheme
import com.vimalcvs.testebud.util.NavigationHost
import com.vimalcvs.testebud.view.fragment.FragmentCategory
import com.vimalcvs.testebud.view.fragment.FragmentFavorite
import com.vimalcvs.testebud.view.fragment.FragmentHome
import com.vimalcvs.testebud.viewmodel.ViewModelMain
import com.vimalcvs.testebud.viewmodel.ViewModelRoom

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TesteBudTheme {
                NavigationHost()
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
fun MainScreen(navController: NavHostController) {
    val navControllerBottom = rememberNavController()
    Scaffold(
        topBar = {
            Toolbar("TesteBud", navController)
        },
        bottomBar = { BottomNavigationBar(navControllerBottom) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationGraph(navController, navControllerBottom)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(title: String, navController: NavController) {
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
                navController.navigate("search") {

                }
            }) {
                Icon(Icons.Outlined.Search, contentDescription = "More")
            }
            IconButton(onClick = {

                navController.navigate("notification") {

                }
            }) {
                Icon(Icons.Outlined.Notifications, contentDescription = "More")
            }
            IconButton(onClick = {

            }) {
                Icon(Icons.Outlined.MoreVert, contentDescription = "More")
            }
        }
    )


}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry.value?.destination?.route
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Rounded.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentDestination == "fragmentHome",
            onClick = {
                navController.navigate("fragmentHome") {
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
            selected = currentDestination == "fragmentCategory",
            onClick = {
                navController.navigate("fragmentCategory") {
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
            selected = currentDestination == "fragmentFavorite",
            onClick = {
                navController.navigate("fragmentFavorite") {
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


@Composable
fun NavigationGraph(navController: NavHostController, navControllerBottom: NavHostController) {
    val mainViewModel: ViewModelMain = viewModel()
    val roomViewModel: ViewModelRoom = viewModel()
    NavHost(navControllerBottom, startDestination = "fragmentHome") {
        composable("fragmentHome") {
            FragmentHome(
                viewModel = mainViewModel,
                navController = navController
            )
        }
        composable("fragmentCategory") {
            FragmentCategory(
                viewModel = roomViewModel,
                navController = navController
            )
        }
        composable("fragmentFavorite") {
            FragmentFavorite(
                viewModel = roomViewModel,
                navController = navController
            )
        }
    }
}

