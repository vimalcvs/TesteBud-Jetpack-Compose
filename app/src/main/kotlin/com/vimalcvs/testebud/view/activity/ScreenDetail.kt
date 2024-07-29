package com.vimalcvs.testebud.view.activity


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.vimalcvs.testebud.model.ModelDetail
import com.vimalcvs.testebud.model.ModelMeal
import com.vimalcvs.testebud.util.EmptyView
import com.vimalcvs.testebud.util.LoadingView
import com.vimalcvs.testebud.util.NoNetworkView
import com.vimalcvs.testebud.util.ToolbarBack
import com.vimalcvs.testebud.viewmodel.ViewModelMain

@Composable
fun DetailScreen(model: ModelMeal, viewModel: ViewModelMain, navController: NavController) {
    val detail by viewModel.detail.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isEmpty by viewModel.isEmpty.observeAsState(false)
    val isNoNetwork by viewModel.isNoNetwork.observeAsState(false)

    LaunchedEffect(key1 = model) {
        viewModel.fetchDetail(detail = model.idMeal.toString())
    }

    Column(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> LoadingView()
            isNoNetwork -> NoNetworkView(viewModel)
            isEmpty -> EmptyView()
            else -> {
                if (detail.isNotEmpty()) {
                    FragmentItemDetailScreen(model = detail[0], navController = navController)
                } else {
                    LoadingView()
                }
            }
        }
    }
}

@Composable
fun FragmentItemDetailScreen(model: ModelDetail, navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        ToolbarBack(model.strMeal) { navController.popBackStack() }
        LazyColumn {
            item {
                Text(text = model.strCategory)
                Text(text = model.strArea)
                Text(text = model.strInstructions)
            }
            item {
                Text(text = model.strMeal)
                Text(text = model.strMealThumb)
            }
        }
    }
}

