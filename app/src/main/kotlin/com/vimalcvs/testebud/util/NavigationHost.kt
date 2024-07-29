package com.vimalcvs.testebud.util

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.vimalcvs.testebud.model.ModelCategory
import com.vimalcvs.testebud.model.ModelMeal
import com.vimalcvs.testebud.view.activity.BoardingOneScreen
import com.vimalcvs.testebud.view.activity.BoardingThreeScreen
import com.vimalcvs.testebud.view.activity.BoardingTwoScreen
import com.vimalcvs.testebud.view.activity.CategoryScreen
import com.vimalcvs.testebud.view.activity.DetailScreen
import com.vimalcvs.testebud.view.activity.LoginScreen
import com.vimalcvs.testebud.view.activity.MainScreen
import com.vimalcvs.testebud.view.activity.NotificationScreen
import com.vimalcvs.testebud.view.activity.RegisterScreen
import com.vimalcvs.testebud.view.activity.SearchScreen
import com.vimalcvs.testebud.view.activity.SplashScreens
import com.vimalcvs.testebud.viewmodel.AuthViewModel
import com.vimalcvs.testebud.viewmodel.ViewModelMain
import com.vimalcvs.testebud.viewmodel.ViewModelRoom

@Composable
fun NavigationHost() {
    val authViewModel: AuthViewModel = viewModel()
    val mainViewModel: ViewModelMain = viewModel()
    val roomViewModel: ViewModelRoom = viewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash", builder = {
        composable("splash") { SplashScreens(navController, authViewModel) }
        composable("home") { MainScreen(navController) }
        composable("boardingOne") { BoardingOneScreen(navController) }
        composable("boardingThree") { BoardingThreeScreen(navController) }
        composable("boardingTwo") { BoardingTwoScreen(navController) }
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("register") { RegisterScreen(navController, authViewModel) }
        composable("search") { SearchScreen(navController, mainViewModel) }
        composable("notification") { NotificationScreen(navController, roomViewModel) }

        composable(
            "category/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryJson = backStackEntry.arguments?.getString("category")
            val category = Gson().fromJson(
                Uri.decode(categoryJson),
                ModelCategory::class.java
            )
            CategoryScreen(category, mainViewModel, navController)
        }

        composable(
            "detail/{detail}",
            arguments = listOf(navArgument("detail") { type = NavType.StringType })
        ) { backStackEntry ->
            val detailJson = backStackEntry.arguments?.getString("detail")
            val detail = Gson().fromJson(
                Uri.decode(detailJson),
                ModelMeal::class.java
            )
            DetailScreen(detail, mainViewModel, navController)
        }

    })
}