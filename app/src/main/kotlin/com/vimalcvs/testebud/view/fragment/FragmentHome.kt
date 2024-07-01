package com.vimalcvs.testebud.view.fragment

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vimalcvs.testebud.R
import com.vimalcvs.testebud.model.ModelCategory
import com.vimalcvs.testebud.model.ModelMeal
import com.vimalcvs.testebud.view.activity.ActivityDetail
import com.vimalcvs.testebud.util.EmptyView
import com.vimalcvs.testebud.util.LoadingView
import com.vimalcvs.testebud.util.NoNetworkView
import com.vimalcvs.testebud.util.TopSlider
import com.vimalcvs.testebud.view.activity.ActivityMore
import com.vimalcvs.testebud.viewmodel.ViewModelMain


@Composable
fun FragmentHome(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FragmentHomeScreen()
    }
}

@Composable
fun FragmentHomeScreen() {
    val viewModel: ViewModelMain = viewModel()
    val breakfast by viewModel.breakfast.observeAsState(emptyList())
    val lamb by viewModel.lamb.observeAsState(emptyList())
    val pasta by viewModel.pasta.observeAsState(emptyList())
    val pork by viewModel.pork.observeAsState(emptyList())
    val side by viewModel.side.observeAsState(emptyList())
    val vegetarian by viewModel.vegetarian.observeAsState(emptyList())
    val slider by viewModel.slider.observeAsState(emptyList())
    val category by viewModel.categories.observeAsState(emptyList())

    val isLoading by viewModel.isLoading.observeAsState(false)
    val isEmpty by viewModel.isEmpty.observeAsState(false)
    val isNoNetwork by viewModel.isNoNetwork.observeAsState(false)

    Column(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> LoadingView()
            isNoNetwork -> NoNetworkView(viewModel)
            isEmpty -> EmptyView()
            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        TopSlider(result = slider, context = LocalContext.current)
                    }
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(category) { item ->
                                ListItemCategorySmall(item = item)
                            }
                        }
                    }
                    item {
                        ListTitle(title = "Breakfast")
                        LazyRow(modifier = Modifier.fillMaxSize()) {
                            items(breakfast) { item ->
                                ListItemMeal(item = item)
                            }
                        }
                    }

                    item {
                        ListTitle(title = "Lamb")
                        LazyRow(modifier = Modifier.fillMaxSize()) {
                            items(lamb) { item ->
                                ListItemMeal(item = item)
                            }
                        }
                    }

                    item {
                        ListTitle(title = "Pasta")
                        LazyRow(modifier = Modifier.fillMaxSize()) {
                            items(pasta) { item ->
                                ListItemMeal(item = item)
                            }
                        }
                    }

                    item {
                        ListTitle(title = "Pork")
                        LazyRow(modifier = Modifier.fillMaxSize()) {
                            items(pork) { item ->
                                ListItemMeal(item = item)
                            }
                        }
                    }

                    item {
                        ListTitle(title = "Side")
                        LazyRow(modifier = Modifier.fillMaxSize()) {
                            items(side) { item ->
                                ListItemMeal(item = item)
                            }
                        }
                    }

                    item {
                        ListTitle(title = "Vegetarian")
                        LazyRow(modifier = Modifier.fillMaxSize()) {
                            items(vegetarian) { item ->
                                ListItemMeal(item = item)
                            }
                        }
                        Spacer(modifier = Modifier.height(60.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun ListTitle(title: String) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp, 8.dp, 0.dp, 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,

            modifier = Modifier
                .weight(1f)
        )

        IconButton(onClick = {
            val intent = Intent(context, ActivityMore::class.java).apply {
                putExtra("itemName", title)
            }
            context.startActivity(intent)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_more),
                contentDescription = "Search",
                modifier = Modifier
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )

        }
    }
}

@Composable
fun ListItemMeal(item: ModelMeal) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(start = 6.dp, end = 0.dp)
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(18.dp))
            .clickable {
                val intent = Intent(context, ActivityDetail::class.java).apply {
                    putExtra("itemName", item.idMeal.toString())
                }
                context.startActivity(intent)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .width(130.dp)
                .height(160.dp)
                .padding(3.dp)
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
                    .clip(shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .width(105.dp)
                .padding(top = 5.dp)
        ) {
            Text(
                text = item.strMeal,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}


@Composable
fun ListItemCategorySmall(item: ModelCategory) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(start = 8.dp)
            .clip(shape = RoundedCornerShape(18.dp))
            .clickable {
                val intent = Intent(context, ActivityDetail::class.java).apply {
                    putExtra("itemName", item.idCategory)
                }
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(18.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(top = 3.dp, bottom = 3.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = item.strCategory,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FragmentHomePosterScreenPreview() {
    ListItemMeal(
        item = ModelMeal(
            1,
            "strCategory",
            "strCategoryThumb"
        )
    )
}