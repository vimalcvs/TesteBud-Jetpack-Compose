package com.vimalcvs.testebud.view.activity


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vimalcvs.testebud.model.ModelDetail
import com.vimalcvs.testebud.theme.TesteBudTheme
import com.vimalcvs.testebud.util.EmptyView
import com.vimalcvs.testebud.util.LoadingView
import com.vimalcvs.testebud.util.NoNetworkView
import com.vimalcvs.testebud.viewmodel.ViewModelMain

class ActivityDetail : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TesteBudTheme {
                val itemName = intent.getStringExtra("itemName") ?: "No Name"
                val viewModel: ViewModelMain = viewModel()
                viewModel.fetchDetail(itemName)
                ItemDetailScreen(viewModel)
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(bottom = systemBars.bottom)
            insets
        }
    }
}

@Composable
fun ItemDetailScreen(viewModel: ViewModelMain) {
    val detail by viewModel.detail.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isEmpty by viewModel.isEmpty.observeAsState(false)
    val isNoNetwork by viewModel.isNoNetwork.observeAsState(false)

    Column(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> LoadingView()
            isNoNetwork -> NoNetworkView(viewModel)
            isEmpty -> EmptyView()
            else -> {
                if (detail.isNotEmpty()) {
                    FragmentItemDetailScreen(model = detail[0])
                } else {
                    LoadingView()
                }
            }
        }
    }
}

@Composable
fun FragmentItemDetailScreen(model: ModelDetail) {
    Column(modifier = Modifier.fillMaxSize()) {
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

@Preview(showBackground = true)
@Composable
fun PreviewItemDetailScreen() {
    TesteBudTheme {
        // ItemDetailScreen(itemName = "Sample Item")
    }
}
