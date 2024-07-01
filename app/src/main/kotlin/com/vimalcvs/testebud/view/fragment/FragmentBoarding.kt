package com.vimalcvs.testebud.view.fragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vimalcvs.testebud.R
import com.vimalcvs.testebud.theme.TesteBudTheme

@Immutable
class BottomEndRoundedShape(private val radius: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            reset()
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height - radius)
            arcTo(
                rect = Rect(
                    left = size.width - 2 * radius,
                    top = size.height - 2 * radius,
                    right = size.width,
                    bottom = size.height
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
fun FragmentBoardingOne(

    navController: NavController
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val imageHeight = remember { (screenHeight * 0.6f) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.food_boarding_one),
                contentDescription = "Food image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .clip(BottomEndRoundedShape(400f)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .height(imageHeight)
            ) {
                Spacer(modifier = Modifier.height(35.dp))
                Text(
                    text = "Translate in Real Time",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Translate your text quickly and easily with our real-time translator. " +
                            "Communicate with confidence, no matter the language.",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp)
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(10.dp)
                                .clip(MaterialTheme.shapes.small)
                                .background(if (it == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))
            }
        }

        Button(
            onClick = { navController.navigate("boardingTwo") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .width(screenWidth * 0.85f)
                .height(50.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "Get Started",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    fontWeight = FontWeight.Normal,
                )
                WelcomeArrowAnimation()
            }
        }
    }
}


@Composable
fun FragmentBoardingTwo(

    navController: NavController
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val imageHeight = remember { (screenHeight * 0.6f) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.food_boarding_two),
                contentDescription = "Food image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .clip(BottomEndRoundedShape(400f)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .height(imageHeight)
            ) {
                Spacer(modifier = Modifier.height(35.dp))
                Text(
                    text = "Translate in Real Time",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Translate your text quickly and easily with our real-time translator. " +
                            "Communicate with confidence, no matter the language.",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp)
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(10.dp)
                                .clip(MaterialTheme.shapes.small)
                                .background(if (it == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))
            }
        }
        Button(
            onClick = { navController.navigate("boardingThree") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .width(screenWidth * 0.85f)
                .height(50.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "Next",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    fontWeight = FontWeight.Normal,
                )
                WelcomeArrowAnimation()
            }
        }
    }
}


@Composable
fun FragmentBoardingThree(
    navController: NavController
) {


    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val imageHeight = remember { (screenHeight * 0.6f) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.food_boarding_three),
                contentDescription = "Food image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .clip(BottomEndRoundedShape(400f)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .height(imageHeight)
            ) {
                Spacer(modifier = Modifier.height(35.dp))
                Text(
                    text = "Translate in Real Time",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Translate your text quickly and easily with our real-time translator. " +
                            "Communicate with confidence, no matter the language.",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp)
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(10.dp)
                                .clip(MaterialTheme.shapes.small)
                                .background(if (it == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))
            }
        }

        Button(
            onClick = {
                navController.navigate("login") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .width(screenWidth * 0.85f)
                .height(50.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "Get Started",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    fontWeight = FontWeight.Normal,
                )
                WelcomeArrowAnimation()
            }
        }
    }
}


@Composable
fun WelcomeArrowAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.iap))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(20.dp)
                .fillMaxWidth()
                .align(Alignment.CenterEnd),
        )
    }
}

@Preview
@Composable
fun FragmentBoardingPreview() {
    TesteBudTheme {
        FragmentBoardingThree(navController = rememberNavController())
    }
}

