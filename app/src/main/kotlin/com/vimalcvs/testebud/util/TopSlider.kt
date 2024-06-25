package com.vimalcvs.testebud.util

import android.content.Context
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vimalcvs.testebud.R
import com.vimalcvs.testebud.model.ModelMeal
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopSlider(
    result: List<ModelMeal>,
    context: Context
) {
    if (result.isEmpty()) {
        Text(
            text = "Loading...",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        return
    }
    val state = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f) { result.size }
    val pageOffset = state.currentPageOffsetFraction

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(state = state) { page ->
            val pageItem by remember {
                mutableIntStateOf(page)
            }
            val currentItem = result[pageItem]
            TopSliderItem(result = currentItem, context = context)
        }
        DotsIndicator(
            pagerState = state,
            modifier = Modifier.padding(8.dp),
            dotSize = 8.dp,
            selectedDotColor = MaterialTheme.colorScheme.primary,
            unselectedDotColor = MaterialTheme.colorScheme.primaryContainer,
        )
        LaunchedEffect(state.currentPage) {
            while (true) {
                delay(4000)
                val nextPage = (state.currentPage + 1) % result.size
                state.animateScrollToPage(
                    nextPage,
                    pageOffsetFraction = pageOffset,
                    animationSpec = spring(
                        dampingRatio = 1f,
                        stiffness = 1f,
                        visibilityThreshold = pageOffset
                    )
                )
            }
        }
    }
}


@Composable
fun TopSliderItem(
    result: ModelMeal,
    context: Context
) {
    Box(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .height(154.dp)
            .clickable {

            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = context)
                .data(result.strMealThumb)
                .crossfade(enable = true)
                .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                .build(), contentDescription = null,

            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 4.dp, start = 4.dp, end = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /*   Text(
                   text = result.strMeal,
                   fontWeight = FontWeight.Normal,
                   textAlign = TextAlign.Center,
                   style = MaterialTheme.typography.labelSmall,
                   color = MaterialTheme.colorScheme.tertiary,
               )

             */
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DotsIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    dotSize: Dp = 8.dp,
    selectedDotColor: Color = MaterialTheme.colorScheme.primary,
    unselectedDotColor: Color = MaterialTheme.colorScheme.primaryContainer,
) {
    val pageCount = pagerState.pageCount
    val currentPage = pagerState.currentPage
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { pageIndex ->
            val dotColor = if (pageIndex == currentPage) selectedDotColor else unselectedDotColor
            val size = if (pageIndex == currentPage) 14.dp else dotSize
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .width(size)
                    .background(color = dotColor, shape = CircleShape)
                    .padding(4.dp)
            )
        }
    }
}