package com.vimalcvs.testebud.view.fragment

import android.net.Uri
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.gson.Gson
import com.vimalcvs.testebud.R
import com.vimalcvs.testebud.model.ModelCategory
import com.vimalcvs.testebud.model.ModelMeal
import com.vimalcvs.testebud.theme.TesteBudTheme
import com.vimalcvs.testebud.util.EmptyView
import com.vimalcvs.testebud.viewmodel.ViewModelRoom

@Composable
fun FragmentFavorite(
    modifier: Modifier = Modifier,
    viewModel: ViewModelRoom = viewModel(),
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FragmentFavoriteScreen(viewModel = viewModel, navController = navController)
    }
}

@Composable
fun FragmentFavoriteScreen(viewModel: ViewModelRoom, navController: NavController) {
    val vegetarian by viewModel.allFavorite.observeAsState(emptyList())
    val isEmpty by viewModel.isEmptyFavorite.observeAsState(false)

    Column {
        when {
            isEmpty -> EmptyView()
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(vegetarian) { item ->
                        ListItemFavorite(item = item, navController = navController)
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
fun ListItemFavorite(item: ModelMeal, navController: NavController) {
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
                    val detailJson = Uri.encode(Gson().toJson(item))
                    navController.navigate("detail/$detailJson")
                }
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data(item.strMealThumb)
                    .crossfade(enable = true)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .build(), contentDescription = null,

                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(24.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = item.strMeal,
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(0.dp, 10.dp, 0.dp, 0.dp)
                .fillMaxSize()
        )
    }
}