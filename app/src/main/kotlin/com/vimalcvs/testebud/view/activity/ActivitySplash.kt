package com.vimalcvs.testebud.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vimalcvs.testebud.R
import com.vimalcvs.testebud.theme.TesteBudTheme
import com.vimalcvs.testebud.viewmodel.AuthState
import com.vimalcvs.testebud.viewmodel.AuthViewModel

class ActivitySplash : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TesteBudTheme {
                SplashScreens()
            }
        }
    }
}

@Composable
fun SplashScreens() {
    val authViewModel: AuthViewModel = viewModel()
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                Handler(Looper.getMainLooper()).postDelayed({
                    context.startActivity(Intent(context, MainActivity::class.java))
                    if (context is Activity) {
                        context.finish()
                    }
                }, 500)
            }

            is AuthState.Unauthenticated, is AuthState.Error -> {
                Handler(Looper.getMainLooper()).postDelayed({
                    context.startActivity(Intent(context, ActivityLogin::class.java))
                    if (context is Activity) {
                        context.finish()
                    }
                }, 500)
            }

            else -> Unit
        }
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.shape_splash_cover),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 50.sp,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.play_to_learn),
                color = Color.White,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(35.dp))
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 35.dp, end = 35.dp)
                    .height(7.dp),
                strokeCap = StrokeCap.Round,
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    TesteBudTheme {
        SplashScreens()
    }
}
