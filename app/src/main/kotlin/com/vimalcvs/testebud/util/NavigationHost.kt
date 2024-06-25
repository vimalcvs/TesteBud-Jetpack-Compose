package com.vimalcvs.testebud.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vimalcvs.testebud.view.activity.FragmentLogin
import com.vimalcvs.testebud.view.activity.FragmentRegister
import com.vimalcvs.testebud.view.fragment.FragmentBoardingOne
import com.vimalcvs.testebud.view.fragment.FragmentBoardingThree
import com.vimalcvs.testebud.view.fragment.FragmentBoardingTwo
import com.vimalcvs.testebud.viewmodel.AuthViewModel

@Composable
fun NavigationHost(modifier: Modifier = Modifier) {
    val authViewModel: AuthViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "boardingOne", builder = {
        composable("boardingOne") {
            FragmentBoardingOne(modifier, navController)
        }
        composable("boardingTwo") {
            FragmentBoardingTwo(modifier, navController)
        }

        composable("boardingThree") {
            FragmentBoardingThree(modifier, navController)
        }

        composable("login") {
            FragmentLogin(modifier, navController, authViewModel)
        }
        composable("signup") {
            FragmentRegister(modifier, navController, authViewModel)
        }
    })
}