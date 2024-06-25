package com.vimalcvs.testebud.view.fragment

import android.content.Intent
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vimalcvs.testebud.R
import com.vimalcvs.testebud.model.ModelCategory
import com.vimalcvs.testebud.theme.TesteBudTheme
import com.vimalcvs.testebud.view.activity.ActivityDetail
import com.vimalcvs.testebud.util.EmptyView
import com.vimalcvs.testebud.util.LoadingView
import com.vimalcvs.testebud.util.NoNetworkView
import com.vimalcvs.testebud.viewmodel.ViewModelRoom

@Composable
fun FragmentCategory(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FragmentCategoryScreen()
    }
}

@Composable
fun FragmentCategoryScreen() {
    val viewModel: ViewModelRoom = viewModel()
    val vegetarian by viewModel.categories.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isEmpty by viewModel.isEmpty.observeAsState(false)
    val isNoNetwork by viewModel.isNoNetwork.observeAsState(false)

    Column {
        when {
            isLoading -> LoadingView()
            isNoNetwork -> NoNetworkView(viewModel)
            isEmpty -> EmptyView()
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(vegetarian) { item ->
                        ListItemCategory(item = item)
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
fun ListItemCategory(item: ModelCategory) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(18.dp))
            .clickable {
                val intent = Intent(context, ActivityDetail::class.java).apply {
                    putExtra("itemName", item.strCategoryDescription)
                }
                context.startActivity(intent)
            }
    ) {
        Card(
            modifier = Modifier
                .padding(3.dp)
                .aspectRatio(1f),
            shape = RoundedCornerShape(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data(item.strCategoryThumb)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .build(), contentDescription = null,

                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = item.strCategory,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(0.dp, 8.dp, 0.dp, 0.dp)
                .fillMaxSize()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun FragmentCategoryPreview() {
    TesteBudTheme {
        ListItemCategory(
            item = ModelCategory(
                "1",
                "strCategory",
                "strCategoryDescription",
                "strCategoryThumb"
            )
        )
    }
}
